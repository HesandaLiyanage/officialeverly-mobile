package com.hess.everly.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class AutographDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autograph_detail)

        val toolbar = findViewById<Toolbar>(R.id.autographDetailToolbar)
        toolbar.title = "Autograph Book"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val autographId = intent.getIntExtra(AutographsActivity.EXTRA_AUTOGRAPH_ID, -1)
        val autograph = EverlyLocalStore.get(this).getAutograph(autographId) ?: run {
            finish()
            return
        }

        findViewById<MaterialCardView>(R.id.autographNotebookCard)
            .setCardBackgroundColor(autograph.coverColor.toColorInt("#FEFCF3"))
        findViewById<TextView>(R.id.autographDetailTitle).text = autograph.title
        findViewById<TextView>(R.id.autographDetailMeta).text =
            "For ${autograph.targetName} • ${DateUtils.formatDisplayDate(autograph.createdAt)}"
        findViewById<TextView>(R.id.autographDetailDescription).text = autograph.description

        val entriesContainer = findViewById<LinearLayout>(R.id.autographEntriesContainer)
        autograph.entries.forEach { entry ->
            val line = layoutInflater.inflate(R.layout.item_autograph_entry, entriesContainer, false)
            line.findViewById<TextView>(R.id.entryAuthor).text = entry.authorName
            line.findViewById<TextView>(R.id.entryMessage).text = entry.message
            entriesContainer.addView(line)
        }
    }
}

