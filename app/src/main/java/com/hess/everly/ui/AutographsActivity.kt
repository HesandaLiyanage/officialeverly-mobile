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

class AutographsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed) // Reusing feed layout which is just a toolbar + recycler

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "My Autographs"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val adapter = AutographsAdapter(emptyList())
        recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.apiService.getAutographs()
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        adapter.updateData(response.body()!!)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AutographsActivity, "Failed to load autographs", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AutographsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
