package com.iago.reminder.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.iago.reminder.models.Word
import java.util.*

object Alarm {
     fun createAlarm(word: Word, context: Context) {
        val time = getTime(word.time)
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("word", word.word)
        intent.putExtra("translate", word.word_translate)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            AlarmManager.INTERVAL_DAY + time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

     fun cancelAlarm(word: Word, context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("word", word.word)
        intent.putExtra("translate", word.word_translate)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun getTime(time: String): Long {
        val (hour, min) = time.split(":").map { it.toInt() }

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)

        return calendar.timeInMillis
    }
}