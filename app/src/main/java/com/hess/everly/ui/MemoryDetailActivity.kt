package com.hess.everly.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R

class MemoryDetailActivity : AppCompatActivity() {

    private lateinit var mediaAdapter: MediaAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_detail)

        val toolbar = findViewById<Toolbar>(R.id.detailToolbar)
        toolbar.title = "Memory Details"
        setSupportActionBar(toolbar)
        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Setup views (in a real app, read from Intent extras or fetch via ID)
        val titleText = findViewById<TextView>(R.id.detailTitle)
        val dateText = findViewById<TextView>(R.id.detailDate)
        val descText = findViewById<TextView>(R.id.detailDesc)
        
        // Mock data
        titleText.text = "Summer Trip 2026"
        dateText.text = "2026-07-15"
        descText.text = "We went to the beach and it was amazing."

        // Setup image grid
        recyclerView = findViewById(R.id.mediaGridRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns for photos
        
        // Mock API load
        mediaAdapter = MediaAdapter(listOf("url1", "url2", "url3", "url4", "url5"))
        recyclerView.adapter = mediaAdapter
    }
}
