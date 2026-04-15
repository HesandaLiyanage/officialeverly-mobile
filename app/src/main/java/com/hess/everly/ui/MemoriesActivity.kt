package com.hess.everly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.data.OfflineMemory

class MemoriesActivity : AppCompatActivity() {

    private lateinit var adapter: MemoriesAdapter
    private lateinit var store: EverlyLocalStore
    private lateinit var emptyState: TextView
    private lateinit var searchInput: EditText
    private lateinit var statsLabel: TextView
    private var allMemories: List<OfflineMemory> = emptyList()
    private var sortDescending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memories)

        val toolbar = findViewById<Toolbar>(R.id.memoriesToolbar)
        toolbar.title = "Memories"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        store = EverlyLocalStore.get(this)
        emptyState = findViewById(R.id.memoriesEmptyState)
        searchInput = findViewById(R.id.memoriesSearchInput)
        statsLabel = findViewById(R.id.memoriesStatsText)

        val recyclerView = findViewById<RecyclerView>(R.id.memoriesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MemoriesAdapter(emptyList()) { memory ->
            startActivity(Intent(this, MemoryDetailActivity::class.java).putExtra(EXTRA_MEMORY_ID, memory.id))
        }
        recyclerView.adapter = adapter

        findViewById<MaterialButton>(R.id.btnCreateMemory).setOnClickListener {
            startActivity(Intent(this, CreateMemoryActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.memoriesSortButton).setOnClickListener {
            sortDescending = !sortDescending
            renderMemories()
        }

        searchInput.doAfterTextChanged { renderMemories() }
    }

    override fun onResume() {
        super.onResume()
        allMemories = store.getMemories()
        renderMemories()
    }

    private fun renderMemories() {
        val query = searchInput.text.toString().trim().lowercase()
        val sorted = if (sortDescending) allMemories.sortedByDescending { it.date } else allMemories.sortedBy { it.date }
        val filtered = sorted.filter {
            it.title.lowercase().contains(query) || it.description.lowercase().contains(query)
        }
        adapter.updateData(filtered)
        emptyState.text = if (query.isBlank()) {
            "No memories yet. Start creating your first memory."
        } else {
            "No memories match \"$query\"."
        }
        emptyState.visibility = if (filtered.isEmpty()) TextView.VISIBLE else TextView.GONE
        statsLabel.text = "Total Memories: ${allMemories.size}"
        findViewById<MaterialButton>(R.id.memoriesSortButton).text =
            if (sortDescending) "Date ↓" else "Date ↑"
    }

    companion object {
        const val EXTRA_MEMORY_ID = "memory_id"
    }
}

