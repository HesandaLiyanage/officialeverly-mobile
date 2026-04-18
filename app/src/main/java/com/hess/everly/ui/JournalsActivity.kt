package com.hess.everly.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.hess.everly.data.OfflineJournal

class JournalsActivity : AppCompatActivity() {

    private lateinit var store: EverlyLocalStore
    private lateinit var adapter: JournalsAdapter
    private lateinit var searchInput: EditText
    private lateinit var emptyState: TextView
    private var allJournals: List<OfflineJournal> = emptyList()
    private var sortDescending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journals)

        val toolbar = findViewById<Toolbar>(R.id.journalsToolbar)
        toolbar.title = "Journals"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        store = EverlyLocalStore.get(this)
        searchInput = findViewById(R.id.journalsSearchInput)
        emptyState = findViewById(R.id.journalsEmptyState)

        val recyclerView = findViewById<RecyclerView>(R.id.journalsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = JournalsAdapter(emptyList()) { journal ->
            startActivity(Intent(this, JournalDetailActivity::class.java).putExtra(EXTRA_JOURNAL_ID, journal.id))
        }
        recyclerView.adapter = adapter

        findViewById<MaterialButton>(R.id.journalsSortButton).setOnClickListener {
            sortDescending = !sortDescending
            render()
        }
        findViewById<MaterialButton>(R.id.btnCreateJournal).setOnClickListener {
            startActivity(Intent(this, CreateJournalActivity::class.java))
        }
        searchInput.doAfterTextChanged { render() }
    }

    override fun onResume() {
        super.onResume()
        allJournals = store.getJournals()
        render()
    }

    private fun render() {
        val query = searchInput.text.toString().trim().lowercase()
        val sorted = if (sortDescending) allJournals.sortedByDescending { it.createdAt } else allJournals.sortedBy { it.createdAt }
        val filtered = sorted.filter {
            it.title.lowercase().contains(query) || it.content.lowercase().contains(query)
        }
        adapter.updateData(filtered)
        emptyState.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        findViewById<MaterialButton>(R.id.journalsSortButton).text =
            if (sortDescending) "Sort: Date" else "Sort: Oldest"
    }

    companion object {
        const val EXTRA_JOURNAL_ID = "journal_id"
    }
}
