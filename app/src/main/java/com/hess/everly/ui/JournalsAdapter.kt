package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.JournalDTO

class JournalsAdapter(private var journals: List<JournalDTO>) :
    RecyclerView.Adapter<JournalsAdapter.JournalViewHolder>() {

    class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.cardTitle)
        val subtitle: TextView = itemView.findViewById(R.id.cardSubtitle)
        val body: TextView = itemView.findViewById(R.id.cardBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generic_card, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val journal = journals[position]
        holder.title.text = journal.title ?: "Untitled Journal"
        holder.subtitle.text = journal.createdAt ?: "Unknown Date"
        holder.body.text = journal.content ?: "No content"
    }

    override fun getItemCount(): Int = journals.size

    fun updateData(newJournals: List<JournalDTO>) {
        journals = newJournals
        notifyDataSetChanged()
    }
}
