package com.example.project3

import android.app.Application
import android.content.SharedPreferences
import android.telephony.SmsManager
import android.util.Log

class WeightTrackerApplication : Application() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("SMSPrefs", MODE_PRIVATE)
    }

    fun sendSMSNotification(message: String): Boolean {
        if (!sharedPreferences.getBoolean("notifications_enabled", false)) {
            return false
        }

        try {
            val smsManager = SmsManager.getDefault()
            // Get the phone number from shared preferences
            val phoneNumber = sharedPreferences.getString("phone_number", null) ?: return false
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            return true
        } catch (e: Exception) {
            Log.e("SMS", "Failed to send SMS", e)
            return false
        }
    }
}