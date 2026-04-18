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

class CreateEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val toolbar = findViewById<Toolbar>(R.id.createEventToolbar)
        toolbar.title = "Create Event"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val titleInput = findViewById<EditText>(R.id.createEventTitleInput)
        val dateInput = findViewById<EditText>(R.id.createEventDateInput)
        val locationInput = findViewById<EditText>(R.id.createEventLocationInput)
        val descriptionInput = findViewById<EditText>(R.id.createEventDescriptionInput)
        dateInput.setText(LocalDate.now().plusDays(7).toString())

        findViewById<MaterialButton>(R.id.submitEventBtn).setOnClickListener {
            val title = titleInput.text.toString().trim()
            val date = dateInput.text.toString().trim()
            val location = locationInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            if (title.isBlank()) {
                titleInput.error = "Event title is required"
                return@setOnClickListener
            }

            EverlyLocalStore.get(this).addEvent(title, description, date, location)
            Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
