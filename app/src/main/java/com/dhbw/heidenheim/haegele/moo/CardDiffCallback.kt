package com.dhbw.heidenheim.haegele.moo

import androidx.recyclerview.widget.DiffUtil
import com.dhbw.heidenheim.haegele.moo.data.domain.Item
import java.time.format.DateTimeFormatter

class CardDiffCallback(
    private val old: List<Item>,
    private val new: List<Item>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].sameDay(new[newPosition].creationTimeStamp)
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}