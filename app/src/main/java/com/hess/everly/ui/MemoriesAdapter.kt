package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.OfflineMemory
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class MemoriesAdapter(
    private var memories: List<OfflineMemory>,
    private val onItemClick: (OfflineMemory) -> Unit
) : RecyclerView.Adapter<MemoriesAdapter.MemoryViewHolder>() {

    class MemoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topPanel: View = itemView.findViewById(R.id.memoryImagePanel)
        val badge: TextView = itemView.findViewById(R.id.memoryBadge)
        val title: TextView = itemView.findViewById(R.id.memoryTitleTextView)
        val date: TextView = itemView.findViewById(R.id.memoryDateTextView)
        val card: MaterialCardView = itemView.findViewById(R.id.memoryCardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memory_card, parent, false)
        return MemoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val memory = memories[position]
        holder.topPanel.setBackgroundColor(memory.accentColor.toColorInt())
        holder.badge.text = "${memory.photos.size} moments"
        holder.title.text = memory.title
        holder.date.text = DateUtils.formatDisplayDate(memory.date)
        holder.card.setOnClickListener { onItemClick(memory) }
    }

    override fun getItemCount(): Int = memories.size

    fun updateData(newMemories: List<OfflineMemory>) {
        memories = newMemories
        notifyDataSetChanged()
    }
}

