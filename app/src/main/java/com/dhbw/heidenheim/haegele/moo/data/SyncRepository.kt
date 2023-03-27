package com.mongodb.app.data

import android.util.Log
import com.dhbw.heidenheim.haegele.moo.app
import com.dhbw.heidenheim.haegele.moo.data.domain.Item
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.SyncSession
import io.realm.kotlin.mongodb.syncSession
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds


/**
 * Repository for accessing Realm Sync.
 */
interface SyncRepository {

    /**
     * Returns a flow with the tasks for the current subscription.
     */
    fun getCardList(depth: Int): ArrayList<Item>?

    /**
     * Adds a task that belongs to the current user using the specified [taskSummary].
     */
    //suspend fun addCard(card: MoodCard)
    suspend fun  addCard(note :String,highlight:String, mood:String)
    /**
     * Updates the Sync subscriptions based on the specified [SubscriptionType].
     */
    suspend fun updateSubscriptions(subscriptionType: SubscriptionType)

    /**
     * Deletes a given task.
     */
    suspend fun deleteItem(item: Item)

    /**
     * Returns the active [SubscriptionType].
     */
    fun getActiveSubscriptionType(realm: Realm? = null): SubscriptionType

    /**
     * Pauses synchronization with MongoDB. This is used to emulate a scenario of no connectivity.
     */
    fun pauseSync()

    /**
     * Resumes synchronization with MongoDB.
     */
    fun resumeSync()

    /**
     * Whether the given [task] belongs to the current user logged in to the app.
     */
    fun isCardMine(cardItem: Item): Boolean

    /**
     * Closes the realm instance held by this repository.
     */
    fun close()
}

/**
 * Repo implementation used in runtime.
 */
class RealmSyncRepository(
    onSyncError: (session: SyncSession, error: SyncException) -> Unit
) : SyncRepository {

    private val realm: Realm
    private val config: SyncConfiguration
    private val currentUser: User
        get() = app.currentUser!!

    init {
        config = SyncConfiguration.Builder(currentUser, setOf(Item::class))
            .initialSubscriptions { realm ->
                // Subscribe to the active subscriptionType - first time defaults to MINE
                val activeSubscriptionType = getActiveSubscriptionType(realm)
                add(getQuery(realm, activeSubscriptionType), activeSubscriptionType.name)
            }
            .errorHandler { session: SyncSession, error: SyncException ->
                onSyncError.invoke(session, error)
            }
            .waitForInitialRemoteData()
            .build()

        realm = Realm.open(config)

        // Mutable states must be updated on the UI thread
        CoroutineScope(Dispatchers.Main).launch {
            realm.subscriptions.waitForSynchronization()
        }
    }

    override fun getCardList(depth: Int): ArrayList<Item>? {
        val items = realm.query<Item>("owner_id == $0", currentUser.id).find().toList()
        val itemOfTheDay = items.find { it.sameDay(LocalDate.now().toString()) }
        val dateComparator = Comparator<Item> { item1, item2 ->
//            val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            LocalDate.parse(item1.creationTimeStamp).compareTo(LocalDate.parse(item2.creationTimeStamp))
        }
        val slicedItems = items.sortedWith(dateComparator)
            .reversed()
            .take(depth + if (itemOfTheDay != null) 1 else 0)
            .toMutableList()
            .apply { itemOfTheDay?.let(::remove) }
        return ArrayList(slicedItems)
    }

    override suspend fun addCard(note :String,highlight:String, mood:String) {
        val gotItem = Item().apply {
            owner_id = currentUser.id
            this.note = note
            this.highlight = highlight
            this.mood = mood
            val now = LocalDate.now().toString()
            this.creationTimeStamp = now
        }
        Log.d("Tag", "Add card to list ")

        val items: RealmResults<Item> = realm.query<Item>("owner_id == $0", currentUser.id)
            .find()

        Log.d("Tag", "Found ${items.count()} Items")

        var matchedItem: Item? = null

        items.forEach { item ->
            if (gotItem.sameDay(item.creationTimeStamp)) {
                Log.d("Tag", "Found same item!")
                matchedItem = item

                // Check for changes and update gotItem if necessary
                if (gotItem.highlight == "" || gotItem.note == "" || gotItem.mood == "") {
                    gotItem.highlight = gotItem.highlight.takeIf { it.isNotBlank() } ?: item.highlight
                    gotItem.note = gotItem.note.takeIf { it.isNotBlank() } ?: item.note
                    gotItem.mood = gotItem.mood.takeIf { it.isNotBlank() } ?: item.mood
                }
            }
        }

// Remove old item if it was found
        matchedItem?.let {
            Log.d("Tag", "Remove old item")
            deleteItem(it)
        }

        Log.d("Tag", "Write to Realm")

        realm.write {
            copyToRealm(gotItem)
        }
    }

    override suspend fun updateSubscriptions(subscriptionType: SubscriptionType) {
        realm.subscriptions.update {
            removeAll()
            val query = when (subscriptionType) {
                SubscriptionType.MINE -> getQuery(realm, SubscriptionType.MINE)
                SubscriptionType.ALL -> getQuery(realm, SubscriptionType.ALL)
            }
            add(query, subscriptionType.name)
        }
    }

    override suspend fun deleteItem(item: Item) {
        realm.write {
            delete(findLatest(item)!!)
        }
        realm.subscriptions.waitForSynchronization(10.seconds)
    }

    override fun getActiveSubscriptionType(realm: Realm?): SubscriptionType {
        val realmInstance = realm ?: this.realm
        val subscriptions = realmInstance.subscriptions
        val firstOrNull = subscriptions.firstOrNull()
        return when (val name = firstOrNull?.name) {
            null,
            SubscriptionType.MINE.name -> SubscriptionType.MINE
            SubscriptionType.ALL.name -> SubscriptionType.ALL
            else -> throw IllegalArgumentException("Invalid Realm Sync subscription: '$name'")
        }
    }

    override fun pauseSync() {
        realm.syncSession.pause()
    }

    override fun resumeSync() {
        realm.syncSession.resume()
    }

    override fun isCardMine(cardItem: Item): Boolean = cardItem.owner_id == currentUser.id

    override fun close() = realm.close()

    private fun getQuery(realm: Realm, subscriptionType: SubscriptionType): RealmQuery<Item> =
        when (subscriptionType) {
            SubscriptionType.MINE -> realm.query("owner_id == $0", currentUser.id)
            SubscriptionType.ALL -> realm.query()
        }
}




/**
 * The two types of subscriptions according to item owner.
 */
enum class SubscriptionType {
    MINE, ALL
}
