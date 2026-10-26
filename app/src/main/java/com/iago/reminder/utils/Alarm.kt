package com.iago.reminder.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.iago.reminder.models.Word
import java.util.Calendar

object Alarm {

    fun createAlarm(word: Word, context: Context) {
        val timeInMillis = getTime(word.time)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("id", word.id)
            putExtra("word", word.word)
            putExtra("translate", word.word_translate)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        timeInMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }
        } catch (_: SecurityException) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelAlarm(word: Word, context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("id", word.id)
            putExtra("word", word.word)
            putExtra("translate", word.word_translate)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )

        if (pendingIntent != null) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            alarmManager?.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

    private fun getTime(time: String): Long {
        val parts = time.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 12
        val min = parts.getOrNull(1)?.substring(0, 2)?.toIntOrNull() ?: 0

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        return calendar.timeInMillis
    }
}
