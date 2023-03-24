package com.dhbw.heidenheim.haegele.moo.data.domain
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.time.LocalDate

class Item() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    var creationTimeStamp: String = ""
    var mood : String = ""
    var highlight : String = ""
    var owner_id: String = ""
    var note : String =""
    constructor(ownerId: String = "") : this() {
        owner_id = ownerId
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Item) return false
        if (this._id != other._id) return false
        if (this.highlight != other.highlight) return false
        if (this.mood != other.mood) return false
        if (this.creationTimeStamp != other.creationTimeStamp) return false
        //if (this.sum != other.sum) return false
        //if(this.card.getDateTime() != other.card.getDateTime()) return false
        if (this.owner_id != other.owner_id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + note.hashCode()
        result = 31 * result + highlight.hashCode()
        result = 31 * result + mood.hashCode()
        result = 31 * result + owner_id.hashCode()
        result = 31 * result + creationTimeStamp.hashCode()
        return result
    }
    fun sameDay(otherTimeStamp: String): Boolean {
        //get the date of the timestamps
        val dateStamp = LocalDate.parse(this.creationTimeStamp)
        val otherDateStamp = LocalDate.parse(otherTimeStamp)
        return dateStamp.isEqual(otherDateStamp)
    }
}
