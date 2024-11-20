package com.example.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.adapter.CardItemAdapter
import com.example.wallet.model.Month

class MonthAdapter(private val months: List<Month>) :
    RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthTitle: TextView = itemView.findViewById(R.id.month_title)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.rv_items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val month = months[position]
        holder.monthTitle.text = month.title

        // Mengatur Adapter untuk RecyclerView di dalam setiap bulan
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CardItemAdapter(month.items ?: emptyList())
        }
    }

    override fun getItemCount(): Int = months.size
}
