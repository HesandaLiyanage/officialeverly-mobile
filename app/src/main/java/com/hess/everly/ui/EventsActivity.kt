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
import com.hess.everly.data.OfflineEvent
import com.hess.everly.util.DateUtils

class EventsActivity : AppCompatActivity() {

    private lateinit var store: EverlyLocalStore
    private lateinit var adapter: EventsAdapter
    private lateinit var searchInput: EditText
    private lateinit var emptyState: TextView
    private var allEvents: List<OfflineEvent> = emptyList()
    private var showingUpcoming = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val toolbar = findViewById<Toolbar>(R.id.eventsToolbar)
        toolbar.title = "Events"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        store = EverlyLocalStore.get(this)
        searchInput = findViewById(R.id.eventsSearchInput)
        emptyState = findViewById(R.id.eventsEmptyState)

        val recyclerView = findViewById<RecyclerView>(R.id.eventsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = EventsAdapter(emptyList()) { event ->
            startActivity(Intent(this, EventDetailActivity::class.java).putExtra(EXTRA_EVENT_ID, event.id))
        }
        recyclerView.adapter = adapter

        findViewById<MaterialButton>(R.id.tabUpcoming).setOnClickListener {
            showingUpcoming = true
            render()
        }
        findViewById<MaterialButton>(R.id.tabPast).setOnClickListener {
            showingUpcoming = false
            render()
        }
        searchInput.doAfterTextChanged { render() }
    }

    override fun onResume() {
        super.onResume()
        allEvents = store.getEvents()
        render()
    }

    private fun render() {
        val query = searchInput.text.toString().trim().lowercase()
        val statusFiltered = allEvents.filter { DateUtils.isUpcoming(it.date) == showingUpcoming }
        val filtered = statusFiltered.filter {
            it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                it.location.lowercase().contains(query)
        }
        adapter.updateData(filtered.sortedBy { it.date })
        emptyState.visibility = if (filtered.isEmpty()) TextView.VISIBLE else TextView.GONE
        findViewById<MaterialButton>(R.id.tabUpcoming).isChecked = showingUpcoming
        findViewById<MaterialButton>(R.id.tabPast).isChecked = !showingUpcoming
    }

    companion object {
        const val EXTRA_EVENT_ID = "event_id"
    }
}

