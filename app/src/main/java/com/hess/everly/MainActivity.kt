package com.hess.everly

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.hess.everly.dto.AuthLoginRequest
import com.hess.everly.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.goToRegisterButton)

        registerButton.setOnClickListener {
            startActivity(android.content.Intent(this, com.hess.everly.ui.RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Perform network request
            performLogin(username, password)
        }
    }

    private fun performLogin(username: String, pass: String) {
        // Launch coroutine on Main thread, then switch to IO for network
        lifecycleScope.launch {
            try {
                val requestDto = AuthLoginRequest(username = username, password = pass)
                
                // Retrofit suspend functions automatically run on a background thread
                val response = ApiClient.apiService.login(requestDto)

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@MainActivity, "Login Successful! Cookie Saved.", Toast.LENGTH_LONG).show()
                        
                        // Navigate to Feed Activity
                        val intent = android.content.Intent(this@MainActivity, com.hess.everly.ui.FeedActivity::class.java)
                        startActivity(intent)
                        finish() // Prevent going back to login screen with back button
                    } else {
                        val errMsg = loginResponse?.errorMessage ?: "Invalid credentials"
                        Toast.makeText(this@MainActivity, errMsg, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Server Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Network failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}