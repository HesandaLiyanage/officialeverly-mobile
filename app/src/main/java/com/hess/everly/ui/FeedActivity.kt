package com.hess.everly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.util.DateUtils

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
        findViewById<TextView>(R.id.homeStatMemories).text = store.getMemories().size.toString()
        findViewById<TextView>(R.id.homeStatJournals).text = store.getJournals().size.toString()
        findViewById<TextView>(R.id.homeStatEvents).text = store.getEvents().count { DateUtils.isUpcoming(it.date) }.toString()

        val latestMemory = store.getMemories().maxByOrNull { it.date }
        val latestJournal = store.getJournals().maxByOrNull { it.createdAt }
        val nextEvent = store.getEvents().filter { DateUtils.isUpcoming(it.date) }.minByOrNull { it.date }

        findViewById<TextView>(R.id.highlightMemoryTitle).text = latestMemory?.title ?: "No memories yet"
        findViewById<TextView>(R.id.highlightMemoryMeta).text =
            latestMemory?.let { DateUtils.formatDisplayDate(it.date) } ?: "Start by adding a memory"
        findViewById<TextView>(R.id.highlightJournalTitle).text = latestJournal?.title ?: "No journals yet"
        findViewById<TextView>(R.id.highlightJournalMeta).text =
            latestJournal?.let { "${DateUtils.formatDisplayDate(it.createdAt)} • ${it.wordCount} words" } ?: "Your writing will appear here"
        findViewById<TextView>(R.id.highlightEventTitle).text = nextEvent?.title ?: "No upcoming events"
        findViewById<TextView>(R.id.highlightEventMeta).text =
            nextEvent?.let { "${DateUtils.formatDisplayDate(it.date)} • ${it.location}" } ?: "Plan your next milestone"

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
