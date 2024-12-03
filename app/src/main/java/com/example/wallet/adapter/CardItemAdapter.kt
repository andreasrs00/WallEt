package com.example.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.model.CardItem

class CardItemAdapter(private val itemList: List<CardItem>) :
    RecyclerView.Adapter<CardItemAdapter.CardItemViewHolder>() {

    class CardItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.transaction_icon)
        val title: TextView = itemView.findViewById(R.id.transaction_title)
        val amount: TextView = itemView.findViewById(R.id.transaction_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.icon.setImageResource(currentItem.icon)
        holder.title.text = currentItem.name
        holder.amount.text = currentItem.amount
    }

    override fun getItemCount(): Int = itemList.size
}
