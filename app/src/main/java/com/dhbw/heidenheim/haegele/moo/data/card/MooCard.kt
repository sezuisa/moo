package com.dhbw.heidenheim.haegele.moo.data.card

import java.time.LocalDateTime

class MoodCard constructor(mood:Mood, highlight:String, note:String){
    //datetime object
    private var creation: LocalDateTime = LocalDateTime.now()

    //Mood
    private var mood:Mood
    //highlight
    private var highlight: String
    //note
    private var note: String

    init {
        this.mood = mood
        this.highlight = highlight
        this.note = note
    }

    public fun getDateTime(): LocalDateTime {
        return creation
    }

    public fun moodState():String {
        return mood.getMood()
    }
    public fun getHighlight():String {
        return highlight
    }
    public fun getNote():String {
        return note
    }
}