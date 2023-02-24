package com.dhbw.heidenheim.haegele.moo

import com.dhbw.heidenheim.haegele.moo.data.card.Happy
import com.dhbw.heidenheim.haegele.moo.data.card.Neutral
import com.dhbw.heidenheim.haegele.moo.data.card.Sad
import org.junit.Assert
import org.junit.Test

class MoodUnitTest {
    @Test
    fun createNewHappyMood()  {

        var curMood = Happy()
        Assert.assertEquals("expect object to return happy", "happy", curMood.getMood())
    }
    @Test
    fun createNewNeutralMood()  {

        var curMood = Neutral()
        Assert.assertEquals("expect object to return neutral", "neutral", curMood.getMood())
    }
    @Test
    fun createNewSadMood()  {

        var curMood = Sad()
        Assert.assertEquals("expect object to return sad", "sad", curMood.getMood())
    }
}