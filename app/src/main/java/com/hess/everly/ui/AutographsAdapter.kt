package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.AutographDTO

class AutographsAdapter(private var autographs: List<AutographDTO>) :
    RecyclerView.Adapter<AutographsAdapter.AutographViewHolder>() {

    class AutographViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.cardTitle)
        val subtitle: TextView = itemView.findViewById(R.id.cardSubtitle)
        val body: TextView = itemView.findViewById(R.id.cardBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutographViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generic_card, parent, false)
        return AutographViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutographViewHolder, position: Int) {
        val autograph = autographs[position]
        holder.title.text = autograph.title ?: "Untitled Autograph"
        holder.subtitle.text = "For: ${autograph.targetName ?: "Unknown"} | Date: ${autograph.createdAt ?: ""}"
        holder.body.text = autograph.description ?: "No description"
    }

    override fun getItemCount(): Int = autographs.size

    fun updateData(newAutographs: List<AutographDTO>) {
        autographs = newAutographs
        notifyDataSetChanged()
    }
}
