package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.MemoryPhoto
import com.hess.everly.util.toColorInt

class MediaAdapter(private var photos: List<MemoryPhoto>) :
    RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.mediaThumbCard)
        val label: TextView = itemView.findViewById(R.id.thumbnailLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_media_thumbnail, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val photo = photos[position]
        holder.card.setCardBackgroundColor(photo.accentColor.toColorInt())
        holder.label.text = photo.label
    }

    override fun getItemCount(): Int = photos.size

    fun updateData(newPhotos: List<MemoryPhoto>) {
        photos = newPhotos
        notifyDataSetChanged()
    }
}
