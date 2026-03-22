package com.hess.everly.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.dto.MemoryDTO
import kotlinx.coroutines.launch

class MemoriesActivity : AppCompatActivity() {

    private lateinit var memoriesAdapter: MemoriesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memories)

        val toolbar = findViewById<Toolbar>(R.id.memoriesToolbar)
        toolbar.title = "My Memories"
        setSupportActionBar(toolbar)
        // Enable back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        recyclerView = findViewById(R.id.memoriesRecyclerView)
        // Use a grid with 2 columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        
        memoriesAdapter = MemoriesAdapter(emptyList()) { selectedMemory ->
            // Click is handled inside the Adapter mostly, but we can do extra logic here
            // Toast.makeText(this, "Clicked: ${selectedMemory.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = memoriesAdapter

        val btnCreateMemory = findViewById<android.widget.Button>(R.id.btnCreateMemory)
        btnCreateMemory.setOnClickListener {
            startActivity(android.content.Intent(this, CreateMemoryActivity::class.java))
        }

        fetchMemories()
    }

    private fun fetchMemories() {
        lifecycleScope.launch {
            try {
                // Future API call:
                // val response = ApiClient.apiService.getMemories()
                
                // --- Mock Data --- 
                val mockMemories = listOf(
                    MemoryDTO(1, "Summer Trip 2026", "A great trip", "2026-07-15", null, false),
                    MemoryDTO(2, "Graduation", "University graduation", "2026-12-05", null, true),
                    MemoryDTO(3, "Dog's Birthday", "He turned 3!", "2026-02-14", null, false)
                )
                memoriesAdapter.updateData(mockMemories)
            } catch (e: Exception) {
                Toast.makeText(this@MemoriesActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
