package com.dhbw.heidenheim.haegele.moo.data.card

abstract class Mood {
    protected var curMood: String = ""

    protected fun setMood(value: String) {
        curMood = value
    }

    abstract fun getMood(): String
}

class Happy : Mood() {
    override fun getMood(): String {
        setMood("happy")
        return curMood
    }
}

class Neutral : Mood() {
    override fun getMood(): String {
        setMood("neutral")
        return curMood
    }
}

class Sad : Mood() {
    override fun getMood(): String {
        setMood("sad")
        return curMood
    }
}