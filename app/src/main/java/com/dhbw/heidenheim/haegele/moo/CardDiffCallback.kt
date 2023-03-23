package com.dhbw.heidenheim.haegele.moo

import androidx.recyclerview.widget.DiffUtil
import com.dhbw.heidenheim.haegele.moo.data.card.MoodCard
import java.time.format.DateTimeFormatter

class CardDiffCallback(
    private val old: List<MoodCard>,
    private val new: List<MoodCard>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd.MMMM.yyyy")
        return old[oldPosition].getDateTime().format(formatter) == new[newPosition].getDateTime().format(formatter)
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}