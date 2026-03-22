package com.hess.everly.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.FeedPostDTO
import com.hess.everly.network.ApiClient
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {

    private lateinit var feedAdapter: FeedAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Feed"
        setSupportActionBar(toolbar)

        // Setup Feature Navigation
        findViewById<android.widget.Button>(R.id.btnGoToMemories).setOnClickListener {
            startActivity(android.content.Intent(this, MemoriesActivity::class.java))
        }
        findViewById<android.widget.Button>(R.id.btnGoToVault).setOnClickListener {
            startActivity(android.content.Intent(this, VaultActivity::class.java))
        }
        findViewById<android.widget.Button>(R.id.btnGoToJournals).setOnClickListener {
            startActivity(android.content.Intent(this, JournalsActivity::class.java))
        }
        findViewById<android.widget.Button>(R.id.btnGoToEvents).setOnClickListener {
            startActivity(android.content.Intent(this, EventsActivity::class.java))
        }
        findViewById<android.widget.Button>(R.id.btnGoToAutographs).setOnClickListener {
            startActivity(android.content.Intent(this, AutographsActivity::class.java))
        }

        // Setup RecyclerView
        recyclerView = findViewById(R.id.feedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedAdapter(emptyList()) // Start empty
        recyclerView.adapter = feedAdapter

        fetchFeedData()
    }

    private fun fetchFeedData() {
        // Run network call
        lifecycleScope.launch {
            try {
                // In EverlyApiService.kt you would have: @GET("api/feed") suspend fun getFeed(): Response<List<FeedPostDTO>>
                
                // val response = ApiClient.apiService.getFeed()
                // if (response.isSuccessful && response.body() != null) {
                //    feedAdapter.updateData(response.body()!!)
                // } else {
                //    Toast.makeText(this@FeedActivity, "Failed to load feed", Toast.LENGTH_SHORT).show()
                // }

                // -- Mock Data for demonstration since Backend /api/feed isn't coded yet --
                val mockPosts = listOf(
                    FeedPostDTO(
                        postId = 1,
                        textContent = "Just pushed the new mobile app UI structure! 🚀",
                        mediaUrl = null,
                        timestamp = "Just now",
                        author = com.hess.everly.dto.UserDTO(1, "hess", "Hesanda Liyanage", null)
                    ),
                    FeedPostDTO(
                        postId = 2,
                        textContent = "Learning how Client-Server architecture actually connects via JSON without relying on heavy frameworks.",
                        mediaUrl = null,
                        timestamp = "2 hours ago",
                        author = com.hess.everly.dto.UserDTO(2, "john", "John Doe", null)
                    )
                )
                feedAdapter.updateData(mockPosts)

            } catch (e: Exception) {
                Toast.makeText(this@FeedActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
