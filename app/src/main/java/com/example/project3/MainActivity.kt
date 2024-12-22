package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var userId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1L)
        if (userId == -1L) {
            // No valid user ID, return to login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupUI()
    }

    private fun setupUI() {
        // Setup Weight Entry button
        findViewById<FloatingActionButton>(R.id.fabAddWeight).setOnClickListener {
            startWeightDatabase()
        }

        // Setup bottom navigation
        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_weight -> {
                    startWeightDatabase()
                    true
                }
                R.id.menu_notifications -> {
                    startNotifications()
                    true
                }
                else -> false
            }
        }
    }

    private fun startWeightDatabase() {
        val intent = Intent(this, activity_weight_database::class.java).apply {
            putExtra("USER_ID", userId)
        }
        startActivity(intent)
    }

    private fun startNotifications() {
        val intent = Intent(this, activity_sms_notification::class.java)
        startActivity(intent)
    }
}