package com.iago.reminder.utils

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.iago.reminder.MainActivity
import com.iago.reminder.R
import com.iago.reminder.ReminderApplication
import com.iago.reminder.utils.Constants.CHANNEL_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult: PendingResult = goAsync()

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scope.launch {
                try {
                    val repo = ReminderApplication.instance.repository
                    val words = repo.getWords()
                    words.filter { it.active }.forEach { word ->
                        Alarm.createAlarm(word, context)
                    }
                } finally {
                    pendingResult.finish()
                }
            }
        } else {
            val word = intent.getStringExtra("word") ?: ""
            val translate = intent.getStringExtra("translate") ?: ""
            val wordId = intent.getIntExtra("id", 0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    pendingResult.finish()
                    return
                }
            }

            val openAppIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra("word_id", wordId)
            }

            val notificationId = (System.currentTimeMillis() % 100000).toInt() + wordId

            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId,
                openAppIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo_notification)
                .setContentTitle("Word Reminder: $word")
                .setContentText("What does \"$word\" mean? Tap to reveal!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Word Reminder: $word")
                        .bigText("Word: $word\nTranslation: $translate\n\nTap to open Word Reminder and practice!")
                )

            val notificationManager = NotificationManagerCompat.from(context)
            try {
                notificationManager.notify(notificationId, builder.build())
            } catch (_: SecurityException) {}

            // Re-schedule for next day if exact alarm was one-shot
            scope.launch {
                try {
                    val repo = ReminderApplication.instance.repository
                    val currentWord = repo.getWord(wordId)
                    if (currentWord != null && currentWord.active) {
                        Alarm.createAlarm(currentWord, context)
                    }
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
