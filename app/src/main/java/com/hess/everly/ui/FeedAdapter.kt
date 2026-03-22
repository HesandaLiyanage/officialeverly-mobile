package com.hess.everly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.FeedPostDTO

class FeedAdapter(private var posts: List<FeedPostDTO>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorName: TextView = itemView.findViewById(R.id.authorNameTextView)
        val timestamp: TextView = itemView.findViewById(R.id.timestampTextView)
        val content: TextView = itemView.findViewById(R.id.postContentTextView)
        val mainImage: ImageView = itemView.findViewById(R.id.postImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feed_post, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val post = posts[position]
        holder.authorName.text = post.author.fullName ?: post.author.username
        holder.timestamp.text = post.timestamp
        holder.content.text = post.textContent

        // If there's an image, we'd normally load it with Glide or Picasso. 
        // For 'no framework' Native, you download the bitmap on a thread or just show a placeholder placeholder for now.
        if (post.mediaUrl.isNullOrEmpty()) {
            holder.mainImage.visibility = View.GONE
        } else {
            holder.mainImage.visibility = View.VISIBLE
            // Implementation mapping URL to image bytes omitted per "no external image loader framework" constraint
            // holder.mainImage.setImageBitmap(downloadBitmap(post.mediaUrl))
        }
    }

    override fun getItemCount(): Int = posts.size

    fun updateData(newPosts: List<FeedPostDTO>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}
