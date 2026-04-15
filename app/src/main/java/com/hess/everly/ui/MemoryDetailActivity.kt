package com.hess.everly.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import com.hess.everly.util.DateUtils
import com.hess.everly.util.toColorInt

class MemoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_detail)

        val toolbar = findViewById<Toolbar>(R.id.detailToolbar)
        toolbar.title = "Memory"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val memoryId = intent.getIntExtra(MemoriesActivity.EXTRA_MEMORY_ID, -1)
        val memory = EverlyLocalStore.get(this).getMemory(memoryId) ?: run {
            finish()
            return
        }

        findViewById<MaterialCardView>(R.id.memoryHero).setCardBackgroundColor(memory.accentColor.toColorInt())
        findViewById<TextView>(R.id.detailTitle).text = memory.title
        findViewById<TextView>(R.id.detailDate).text = DateUtils.formatDisplayDate(memory.date)
        findViewById<TextView>(R.id.detailDesc).text = memory.description
        findViewById<TextView>(R.id.detailMeta).text =
            if (memory.isPublic) "Public memory • ${memory.photos.size} moments" else "Private memory • ${memory.photos.size} moments"

        val recyclerView = findViewById<RecyclerView>(R.id.mediaGridRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MediaAdapter(memory.photos)
    }
}

