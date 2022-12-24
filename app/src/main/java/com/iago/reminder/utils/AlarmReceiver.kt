package com.iago.reminder.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iago.reminder.MainActivity
import com.iago.reminder.R
import com.iago.reminder.utils.Constants.CHANNEL_ID

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val word = p1.getStringExtra("word")
        val translate = p1.getStringExtra("translate")

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("Reminder")
                .setContentText("$word means $translate")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }


}