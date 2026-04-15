package com.hess.everly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.data.OfflineAutograph

class AutographsActivity : AppCompatActivity() {

    private lateinit var store: EverlyLocalStore
    private lateinit var adapter: AutographsAdapter
    private lateinit var searchInput: EditText
    private lateinit var emptyState: TextView
    private var autographs: List<OfflineAutograph> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autographs)

        val toolbar = findViewById<Toolbar>(R.id.autographsToolbar)
        toolbar.title = "Autographs"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        store = EverlyLocalStore.get(this)
        searchInput = findViewById(R.id.autographsSearchInput)
        emptyState = findViewById(R.id.autographsEmptyState)

        val recyclerView = findViewById<RecyclerView>(R.id.autographsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AutographsAdapter(emptyList()) { autograph ->
            startActivity(Intent(this, AutographDetailActivity::class.java).putExtra(EXTRA_AUTOGRAPH_ID, autograph.id))
        }
        recyclerView.adapter = adapter

        searchInput.doAfterTextChanged { render() }
    }

    override fun onResume() {
        super.onResume()
        autographs = store.getAutographs()
        render()
    }

    private fun render() {
        val query = searchInput.text.toString().trim().lowercase()
        val filtered = autographs.filter {
            it.title.lowercase().contains(query) ||
                it.targetName.lowercase().contains(query) ||
                it.description.lowercase().contains(query)
        }
        adapter.updateData(filtered)
        emptyState.visibility = if (filtered.isEmpty()) TextView.VISIBLE else TextView.GONE
    }

    companion object {
        const val EXTRA_AUTOGRAPH_ID = "autograph_id"
    }
}

