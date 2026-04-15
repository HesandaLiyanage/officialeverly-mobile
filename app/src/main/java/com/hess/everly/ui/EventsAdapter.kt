package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.OfflineEvent
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class EventsAdapter(
    private var events: List<OfflineEvent>,
    private val onItemClick: (OfflineEvent) -> Unit
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: View = itemView.findViewById(R.id.eventImagePanel)
        val title: TextView = itemView.findViewById(R.id.eventTitle)
        val subtitle: TextView = itemView.findViewById(R.id.eventSubtitle)
        val body: TextView = itemView.findViewById(R.id.eventBody)
        val status: TextView = itemView.findViewById(R.id.eventStatus)
        val card: MaterialCardView = itemView.findViewById(R.id.eventCardRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        val upcoming = DateUtils.isUpcoming(event.date)
        holder.image.setBackgroundColor(event.accentColor.toColorInt())
        holder.title.text = event.title
        holder.subtitle.text = "${DateUtils.formatDisplayDate(event.date)} • ${event.location}"
        holder.body.text = event.description
        holder.status.text = if (upcoming) "Upcoming" else "Past event"
        holder.card.setOnClickListener { onItemClick(event) }
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<OfflineEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }
}

