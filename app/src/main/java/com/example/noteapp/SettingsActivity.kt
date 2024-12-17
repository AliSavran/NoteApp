package com.example.noteapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapp.Utils.NotificationPreferences
import com.example.noteapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var notificationPreferences: NotificationPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationPreferences = NotificationPreferences(this)

        binding.switchNotifications.isChecked =
            notificationPreferences.isNotificationEnabled()

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            notificationPreferences.setNotificationEnabled(isChecked)

            val message = if (isChecked)
                "Bildirimler etkinleştirildi"
            else
                "Bildirimler devre dışı bırakıldı"

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }
}