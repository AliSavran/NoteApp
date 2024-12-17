package com.example.noteapp.Utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.noteapp.MainActivity
import com.example.noteapp.R

class NotificationHelper(
    private val context:Context,
    private val notificationPreferences: NotificationPreferences
) {

    private val CHANNEL_ID = "NoteAppChannel"
    private val CHANNEL_NAME = "Not Bildirimleri"
    private val NOTIFICATION_ID = 1


    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val channel = NotificationChannel(
                CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Not Uygulaması Bildirimleri"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun showNoteReminderNotification(title: String, content : String){

        if (!notificationPreferences.isNotificationEnabled()){
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle("Not Hatırlatıcısı")
            .setContentText("$title: ${content.take(50)}...")
            .setSmallIcon(R.drawable.reminder)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID,notification)

    }
}