package com.iago.reminder.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iago.reminder.MainActivity
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.utils.Constants.CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderDao: ReminderDao

    private val scope = CoroutineScope(SupervisorJob())

    override fun onReceive(context: Context, p1: Intent) {
        val pendingResult: PendingResult = goAsync()

        if (p1.action == "android.intent.action.BOOT_COMPLETED") {
            scope.launch(Dispatchers.Default) {
                val words = reminderDao.getWords()
                words.forEach { word ->
                    if (word.active) {
                        Log.d("TAG", word.word)
                        Alarm.createAlarm(word, context)
                    }

                }
                pendingResult.finish()
            }

        } else {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            val word = p1.getStringExtra("word")
            val translate = p1.getStringExtra("translate")

            val code = System.currentTimeMillis().toInt()

            val pendingIntent = PendingIntent.getActivity(
                context,
                code,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.logo_notification)
                    .setContentTitle("Reminder")
                    .setContentText("What does $word means?")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .setBigContentTitle("What does $word mean?")
                            .bigText("It's $translate")
                    )

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(code, builder.build())
        }
    }

}