package com.hess.everly.ui

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.OfflineJournal
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class JournalsAdapter(
    private var journals: List<OfflineJournal>,
    private val onItemClick: (OfflineJournal) -> Unit
) : RecyclerView.Adapter<JournalsAdapter.JournalViewHolder>() {

    class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: View = itemView.findViewById(R.id.journalHeader)
        val title: TextView = itemView.findViewById(R.id.journalTitle)
        val subtitle: TextView = itemView.findViewById(R.id.journalSubtitle)
        val body: TextView = itemView.findViewById(R.id.journalBody)
        val card: MaterialCardView = itemView.findViewById(R.id.journalCardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_journal_card, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val journal = journals[position]
        holder.header.background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                journal.accentStartColor.toColorInt(),
                journal.accentEndColor.toColorInt()
            )
        )
        holder.title.text = journal.title
        holder.subtitle.text = "${DateUtils.formatDisplayDate(journal.createdAt)} • ${journal.wordCount} words"
        holder.body.text = journal.content
        holder.card.setOnClickListener { onItemClick(journal) }
    }

    override fun getItemCount(): Int = journals.size

    fun updateData(newJournals: List<OfflineJournal>) {
        journals = newJournals
        notifyDataSetChanged()
    }
}

