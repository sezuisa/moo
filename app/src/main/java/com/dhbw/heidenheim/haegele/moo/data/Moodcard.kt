package com.dhbw.heidenheim.haegele.moo.data

import java.time.DateTimeException
import java.util.Date

class Moodcard constructor(creation:Date,mood:Mood,highlight:String, note:String){
    //datetime object
    private lateinit var creation:Date
    //Mood
    private lateinit var mood:Mood
    //highlight
    private lateinit var highlight: String
    //note
    private lateinit var note: String

    init {
        this.creation = creation
        this.mood = mood
        this.highlight = highlight
        this.note = note
    }

    public fun getDate(): Date {
        return creation
    }

    public fun getMood() {
        return mood.getMood()
    }
}

