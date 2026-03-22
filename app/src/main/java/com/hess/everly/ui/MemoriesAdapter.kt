package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.MemoryDTO

class MemoriesAdapter(
    private var memories: List<MemoryDTO>,
    private val onItemClick: (MemoryDTO) -> Unit
) : RecyclerView.Adapter<MemoriesAdapter.MemoryViewHolder>() {

    class MemoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverImage: ImageView = itemView.findViewById(R.id.memoryCoverImage)
        val title: TextView = itemView.findViewById(R.id.memoryTitleTextView)
        val date: TextView = itemView.findViewById(R.id.memoryDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memory_card, parent, false)
        return MemoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        val memory = memories[position]
        holder.title.text = memory.title
        holder.date.text = memory.date
        
        // Handle image loading natively here 
        // if (memory.coverMediaUrl != null) { ... }

        holder.itemView.setOnClickListener {
            onItemClick(memory)
            val context = holder.itemView.context
            context.startActivity(android.content.Intent(context, MemoryDetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int = memories.size

    fun updateData(newMemories: List<MemoryDTO>) {
        memories = newMemories
        notifyDataSetChanged()
    }
}
