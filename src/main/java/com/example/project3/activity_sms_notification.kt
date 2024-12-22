package com.example.project3

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class activity_sms_notification : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        when {
            isGranted -> {
                enableNotifications()
            }
            else -> {
                disableNotifications()
                Toast.makeText(
                    this,
                    "SMS notifications will not be available",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_notification)

        sharedPreferences = getSharedPreferences("SMSPrefs", MODE_PRIVATE)

        setupUI()
    }

    private fun setupUI() {
        findViewById<MaterialButton>(R.id.allowButton).setOnClickListener {
            checkAndRequestSMSPermission()
        }

        findViewById<MaterialButton>(R.id.denyButton).setOnClickListener {
            disableNotifications()
            finish()
        }
    }

    private fun checkAndRequestSMSPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableNotifications()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("SMS Permission Required")
            .setMessage("SMS permission is needed to send notifications about your weight tracking progress.")
            .setPositiveButton("Grant Permission") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            .setNegativeButton("Deny") { _, _ ->
                disableNotifications()
            }
            .show()
    }

    private fun enableNotifications() {
        sharedPreferences.edit().putBoolean("notifications_enabled", true).apply()
        Toast.makeText(this, "SMS notifications enabled", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun disableNotifications() {
        sharedPreferences.edit().putBoolean("notifications_enabled", false).apply()
        Toast.makeText(this, "SMS notifications disabled", Toast.LENGTH_SHORT).show()
        finish()
    }
}