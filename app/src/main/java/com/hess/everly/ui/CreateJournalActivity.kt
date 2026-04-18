package com.hess.everly.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.hess.everly.R
import com.hess.everly.data.EverlyLocalStore
import java.time.LocalDate

class CreateJournalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_journal)

        val toolbar = findViewById<Toolbar>(R.id.createJournalToolbar)
        toolbar.title = "New Journal"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val titleInput = findViewById<EditText>(R.id.createJournalTitleInput)
        val contentInput = findViewById<EditText>(R.id.createJournalContentInput)
        val dateInput = findViewById<EditText>(R.id.createJournalDateInput)
        dateInput.setText(LocalDate.now().toString())

        findViewById<MaterialButton>(R.id.submitJournalBtn).setOnClickListener {
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()
            val date = dateInput.text.toString().trim()

            if (title.isBlank()) {
                titleInput.error = "Title is required"
                return@setOnClickListener
            }

            EverlyLocalStore.get(this).addJournal(title, content, date)
            Toast.makeText(this, "Journal saved on this device", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

