package com.hess.everly.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val toolbar = findViewById<Toolbar>(R.id.eventDetailToolbar)
        toolbar.title = "Event"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val eventId = intent.getIntExtra(EventsActivity.EXTRA_EVENT_ID, -1)
        val event = EverlyLocalStore.get(this).getEvent(eventId) ?: run {
            finish()
            return
        }

        findViewById<MaterialCardView>(R.id.eventHeroCard).setCardBackgroundColor(event.accentColor.toColorInt())
        findViewById<TextView>(R.id.eventDetailTitle).text = event.title
        findViewById<TextView>(R.id.eventDetailMeta).text =
            "${DateUtils.formatDisplayDate(event.date)} • ${event.location}"
        findViewById<TextView>(R.id.eventDetailDescription).text = event.description
    }
}
