package com.hess.everly.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class JournalDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_detail)

        val toolbar = findViewById<Toolbar>(R.id.journalDetailToolbar)
        toolbar.title = "Journal"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val journalId = intent.getIntExtra(JournalsActivity.EXTRA_JOURNAL_ID, -1)
        val journal = EverlyLocalStore.get(this).getJournal(journalId) ?: run {
            finish()
            return
        }

        findViewById<MaterialCardView>(R.id.journalDetailHero).background = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(journal.accentStartColor.toColorInt(), journal.accentEndColor.toColorInt())
        )
        findViewById<TextView>(R.id.journalDetailTitle).text = journal.title
        findViewById<TextView>(R.id.journalDetailMeta).text =
            "${DateUtils.formatDisplayDate(journal.createdAt)} • ${journal.wordCount} words"
        findViewById<TextView>(R.id.journalDetailBody).text = journal.content
    }
}

