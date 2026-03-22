package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.EventDTO

class EventsAdapter(private var events: List<EventDTO>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.cardTitle)
        val subtitle: TextView = itemView.findViewById(R.id.cardSubtitle)
        val body: TextView = itemView.findViewById(R.id.cardBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_generic_card, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.eventName ?: "Untitled Event"
        holder.subtitle.text = "${event.eventDate ?: ""} - ${event.eventLocation ?: ""}"
        holder.body.text = event.description ?: "No description"
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<EventDTO>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
