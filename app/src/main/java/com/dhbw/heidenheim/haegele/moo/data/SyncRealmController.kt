package com.dhbw.heidenheim.haegele.moo.data

import com.mongodb.app.data.RealmSyncRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SyncRealmController {
    val repository = RealmSyncRepository { _, error ->
        // Sync errors come from a background thread so route the Toast through the UI thread
        GlobalScope.launch {
        }
    }

    fun getRepo(): RealmSyncRepository{return  repository}
    fun addCard(s: String, s1: String, s2: String) {
        GlobalScope.launch { repository.addCard(s,s1,s2) }

    }
}