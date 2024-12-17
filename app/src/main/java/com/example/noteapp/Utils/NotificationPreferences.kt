package com.example.noteapp.Utils

import android.content.Context
import android.content.SharedPreferences

class NotificationPreferences(context:Context) {

    private val prefs:SharedPreferences = context.getSharedPreferences("NoteAppPrefs",Context.MODE_PRIVATE)

    fun setNotificationEnabled(isEnabled : Boolean){
        prefs.edit().putBoolean("notifications_enabled",isEnabled).apply()
    }

    fun isNotificationEnabled():Boolean{
        return prefs.getBoolean("notifications_enabled", true)
    }
}