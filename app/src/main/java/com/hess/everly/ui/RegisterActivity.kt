package com.hess.everly.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hess.everly.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerBtn = findViewById<Button>(R.id.registerButton)
        registerBtn.setOnClickListener {
            Toast.makeText(this, "Registration backend API not connected yet.", Toast.LENGTH_SHORT).show()
        }
    }
}
