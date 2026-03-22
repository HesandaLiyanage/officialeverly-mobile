package com.hess.everly.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.hess.everly.R
import com.hess.everly.dto.VaultVerifyRequest
import com.hess.everly.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VaultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "My Vault"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.feedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val adapter = JournalsAdapter(emptyList()) // Vault currently returns journals
        recyclerView.adapter = adapter

        // Prompt for Vault Password
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        
        AlertDialog.Builder(this)
            .setTitle("Enter Vault Password")
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("Unlock") { _, _ ->
                val password = input.text.toString()
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val request = VaultVerifyRequest(password)
                        val response = ApiClient.apiService.verifyVault(request)
                        if (response.isSuccessful && response.body()?.success == true) {
                            withContext(Dispatchers.Main) {
                                adapter.updateData(response.body()?.journals ?: emptyList())
                                Toast.makeText(this@VaultActivity, "Vault unlocked", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@VaultActivity, "Access Denied: ${response.body()?.error}", Toast.LENGTH_SHORT).show()
                                finish() // close activity if failed
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@VaultActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                finish()
            }
            .show()
    }
}
