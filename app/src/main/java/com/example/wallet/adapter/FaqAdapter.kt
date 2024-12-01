package com.example.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.model.DataFaq

class FaqAdapter(private val faqList: List<DataFaq>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llQuestion: LinearLayout = itemView.findViewById(R.id.ll_question)
        val llAnswer: LinearLayout = itemView.findViewById(R.id.ll_answer)
        val ivDropdown: ImageView = itemView.findViewById(R.id.ivdropdown)
        val tvAnswer: TextView = itemView.findViewById(R.id.tv_answer)
        val tvQuestion: TextView = itemView.findViewById(R.id.tv_isifaq)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_item, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val faq = faqList[position]

        holder.tvQuestion.text = faq.question
        holder.tvAnswer.text = faq.answer

        // Mengatur visibilitas elemen
        holder.llAnswer.visibility = View.GONE
        holder.tvAnswer.visibility = View.GONE
        holder.ivDropdown.rotation = 0f

        // Set listener untuk klik item
        holder.llQuestion.setOnClickListener {
            if (holder.llAnswer.visibility == View.GONE) {
                holder.llAnswer.visibility = View.VISIBLE
                holder.tvAnswer.visibility = View.VISIBLE
                holder.ivDropdown.rotation = 180f

                val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
                holder.llAnswer.startAnimation(slideDown)
            } else {
                holder.llAnswer.visibility = View.GONE
                holder.tvAnswer.visibility = View.GONE
                holder.ivDropdown.rotation = 0f

                val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                holder.llAnswer.startAnimation(slideUp)
            }
        }
    }

    override fun getItemCount(): Int = faqList.size
}
