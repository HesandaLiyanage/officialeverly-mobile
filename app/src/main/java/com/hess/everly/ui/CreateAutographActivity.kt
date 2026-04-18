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

class CreateAutographActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_autograph)

        val toolbar = findViewById<Toolbar>(R.id.createAutographToolbar)
        toolbar.title = "Create Autograph Book"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val titleInput = findViewById<EditText>(R.id.createAutographTitleInput)
        val targetInput = findViewById<EditText>(R.id.createAutographTargetInput)
        val descriptionInput = findViewById<EditText>(R.id.createAutographDescriptionInput)
        val authorInput = findViewById<EditText>(R.id.createAutographAuthorInput)
        val messageInput = findViewById<EditText>(R.id.createAutographMessageInput)

        findViewById<MaterialButton>(R.id.submitAutographBtn).setOnClickListener {
            val title = titleInput.text.toString().trim()
            val target = targetInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val author = authorInput.text.toString().trim()
            val message = messageInput.text.toString().trim()

            if (title.isBlank()) {
                titleInput.error = "Book title is required"
                return@setOnClickListener
            }

            EverlyLocalStore.get(this).addAutograph(
                title = title,
                targetName = target,
                description = description,
                createdAt = LocalDate.now().toString(),
                firstAuthor = author,
                firstMessage = message
            )
            Toast.makeText(this, "Autograph book created", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

