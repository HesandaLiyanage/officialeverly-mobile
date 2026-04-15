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

class CreateMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memory)

        val toolbar = findViewById<Toolbar>(R.id.createToolbar)
        toolbar.title = "Add Memory"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val titleInput = findViewById<EditText>(R.id.createTitleInput)
        val descriptionInput = findViewById<EditText>(R.id.createDescriptionInput)
        val dateInput = findViewById<EditText>(R.id.createDateInput)
        dateInput.setText(LocalDate.now().toString())

        findViewById<MaterialButton>(R.id.submitMemoryBtn).setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val date = dateInput.text.toString().trim()

            if (title.isBlank()) {
                titleInput.error = "Title is required"
                return@setOnClickListener
            }

            EverlyLocalStore.get(this).addMemory(title, description, date)
            Toast.makeText(this, "Memory saved on this device", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

