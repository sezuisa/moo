package com.dhbw.heidenheim.haegele.moo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dhbw.heidenheim.haegele.moo.data.card.MoodCard
import com.dhbw.heidenheim.haegele.moo.databinding.ItemCardBinding
import java.time.format.DateTimeFormatter

class CardStackAdapter(
    private var cards: List<MoodCard> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val formatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy")
        val date = card.getDateTime().format(formatter)
        holder.date.text = date
        holder.highlight.text = card.getHighlight()
        holder.note.text = card.getNote()
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, date, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setCards(spots: List<MoodCard>) {
        this.cards = spots
    }

    fun getCards(): List<MoodCard> {
        return cards
    }

    inner class ViewHolder(binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val date: TextView = binding.itemDate
        var highlight: TextView = binding.itemHighlight
        var note: TextView = binding.itemFreetext
    }

}