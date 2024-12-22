package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize database helper
        dbHelper = DatabaseHelper(this)

        // Initialize UI components
        setupUI()
    }

    private fun setupUI() {
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)

        // Login button
        findViewById<MaterialButton>(R.id.loginButton).setOnClickListener {
            handleLogin()
        }

        // Create account button
        findViewById<MaterialButton>(R.id.createAccountButton).setOnClickListener {
            handleRegistration()
        }
    }

    private fun handleLogin() {
        val username = usernameInput.text?.toString()?.trim() ?: ""
        val password = passwordInput.text?.toString()?.trim() ?: ""

        if (validateInput(username, password)) {
            val userId = dbHelper.validateUser(username, password)
            if (userId != -1L) {
                startMainActivity(userId)
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleRegistration() {
        val username = usernameInput.text?.toString()?.trim() ?: ""
        val password = passwordInput.text?.toString()?.trim() ?: ""

        if (validateInput(username, password)) {
            try {
                val userId = dbHelper.addUser(username, password)
                if (userId != -1L) {
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                    startMainActivity(userId)
                } else {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error creating account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            usernameInput.error = "Username is required"
            return false
        }
        if (password.isEmpty()) {
            passwordInput.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            passwordInput.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }

    private fun startMainActivity(userId: Long) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USER_ID", userId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}