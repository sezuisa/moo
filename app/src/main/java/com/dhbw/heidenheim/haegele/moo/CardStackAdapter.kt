package com.dhbw.heidenheim.haegele.moo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.dhbw.heidenheim.haegele.moo.data.domain.Item
import com.dhbw.heidenheim.haegele.moo.databinding.ItemCardBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CardStackAdapter(
    private var cards: List<Item> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val dateStamp = LocalDate.parse(card.creationTimeStamp).format(formatter)
        holder.creationTimeStamp.text = dateStamp
        holder.highlight.text = card.highlight
        holder.note.text = card.note

        when (card.mood) {
            "happy" -> {
                holder.moodHappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.ic_green, null))
                holder.moodNeutral.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
                holder.moodUnhappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
            }
            "neutral" -> {
                holder.moodHappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
                holder.moodNeutral.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.ic_yellow, null))
                holder.moodUnhappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
            }
            "unhappy" -> {
                holder.moodHappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
                holder.moodNeutral.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.grey, null))
                holder.moodUnhappy.drawable.setTint(ResourcesCompat.getColor(MooApp.res, R.color.ic_red, null))
            }
        }

        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, dateStamp, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setCards(spots: List<Item>) {
        this.cards = spots
    }

    fun getCards(): List<Item> {
        return cards
    }

    inner class ViewHolder(binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        val creationTimeStamp: TextView = binding.itemDate
        var highlight: TextView = binding.itemHighlight
        var note: TextView = binding.itemFreetext
        var moodHappy: ImageView = binding.itemMoodHappy
        var moodNeutral: ImageView = binding.itemMoodNeutral
        var moodUnhappy: ImageView = binding.itemMoodUnhappy
    }

}