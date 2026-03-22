package com.hess.everly.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hess.everly.R

class CreateMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memory)

        val toolbar = findViewById<Toolbar>(R.id.createToolbar)
        toolbar.title = "Upload Memory"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val submitBtn = findViewById<Button>(R.id.submitMemoryBtn)
        val fileBtn = findViewById<Button>(R.id.selectFilesBtn)

        fileBtn.setOnClickListener {
            Toast.makeText(this, "Android native file picker would open here.", Toast.LENGTH_SHORT).show()
        }

        submitBtn.setOnClickListener {
            val title = findViewById<EditText>(R.id.createTitleInput).text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Ready to send multipart API request to backend!", Toast.LENGTH_LONG).show()
            // Here you would use Retrofit @Multipart logic
        }
    }
}
