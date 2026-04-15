package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.OfflineAutograph
import com.hess.everly.util.DateUtils

class AutographsAdapter(
    private var autographs: List<OfflineAutograph>,
    private val onItemClick: (OfflineAutograph) -> Unit
) : RecyclerView.Adapter<AutographsAdapter.AutographViewHolder>() {

    class AutographViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.autographTitle)
        val subtitle: TextView = itemView.findViewById(R.id.autographSubtitle)
        val body: TextView = itemView.findViewById(R.id.autographBody)
        val count: TextView = itemView.findViewById(R.id.autographCount)
        val card: MaterialCardView = itemView.findViewById(R.id.autographCardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutographViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_autograph_card, parent, false)
        return AutographViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutographViewHolder, position: Int) {
        val autograph = autographs[position]
        holder.title.text = autograph.title
        holder.subtitle.text = "For ${autograph.targetName} • ${DateUtils.formatDisplayDate(autograph.createdAt)}"
        holder.body.text = autograph.description
        holder.count.text = "${autograph.entries.size} signatures"
        holder.card.setOnClickListener { onItemClick(autograph) }
    }

    override fun getItemCount(): Int = autographs.size

    fun updateData(newAutographs: List<OfflineAutograph>) {
        autographs = newAutographs
        notifyDataSetChanged()
    }
}

