package com.dhbw.heidenheim.haegele.moo


import com.dhbw.heidenheim.haegele.moo.data.card.Happy
import com.dhbw.heidenheim.haegele.moo.data.card.MoodCard
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Objects.isNull

class MoodCardUnitTest {

    @Test
    fun createNewCard() {
        var newHappyCard = MoodCard(
            Happy(),
            "Staubsaugen",
            "hier ist echt viel Staub"
        )
        assertEquals("check if the param note, can access over the get-method", newHappyCard.getNote(),"hier ist echt viel Staub")
        assertEquals("check if the param highlight, can access over the get-method", newHappyCard.getHighlight(),"Staubsaugen")
        assertEquals("Check if the mood object got the value happy",newHappyCard.moodState(),"happy")
        assertEquals("Check if the card has a value",isNull(newHappyCard.getDateTime()), false)
    }
}