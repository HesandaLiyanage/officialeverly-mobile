package com.hess.everly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Everly"
        setSupportActionBar(toolbar)

        val store = EverlyLocalStore.get(this)
        findViewById<TextView>(R.id.homeSummary).text =
            "${store.getMemories().size} memories • ${store.getJournals().size} journals • ${store.getAutographs().size} autograph books • ${store.getEvents().size} events"

        bindModuleCard(R.id.cardMemories, MemoriesActivity::class.java)
        bindModuleCard(R.id.cardJournals, JournalsActivity::class.java)
        bindModuleCard(R.id.cardAutographs, AutographsActivity::class.java)
        bindModuleCard(R.id.cardEvents, EventsActivity::class.java)
    }

    private fun bindModuleCard(cardId: Int, destination: Class<*>) {
        findViewById<MaterialCardView>(cardId).setOnClickListener {
            startActivity(Intent(this, destination))
        }
    }
}

