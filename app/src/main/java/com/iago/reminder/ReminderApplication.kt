package com.iago.reminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.iago.reminder.database.ReminderDatabase
import com.iago.reminder.repository.ReminderRepository
import com.iago.reminder.utils.Constants.CHANNEL_ID
import com.iago.reminder.utils.Constants.CHANNEL_NAME

class ReminderApplication : Application() {

    lateinit var database: ReminderDatabase
        private set

    lateinit var repository: ReminderRepository
        private set

    companion object {
        lateinit var instance: ReminderApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = ReminderDatabase.getInstance(this)
        repository = ReminderRepository(database.getDao())
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Daily reminders to practice your vocabulary"
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
