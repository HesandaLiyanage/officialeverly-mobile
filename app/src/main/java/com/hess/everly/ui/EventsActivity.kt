package com.hess.everly.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed) // Reusing feed layout

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Events"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val adapter = EventsAdapter(emptyList())
        recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.apiService.getEvents()
                if (response.isSuccessful && response.body() != null) {
                    val eventsResponse = response.body()!!
                    // Combine past and upcoming
                    val allEvents = buildList {
                        eventsResponse.upcoming?.let { addAll(it) }
                        eventsResponse.past?.let { addAll(it) }
                    }
                    withContext(Dispatchers.Main) {
                        adapter.updateData(allEvents)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@EventsActivity, "Failed to load events", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EventsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
