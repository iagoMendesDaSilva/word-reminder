package com.iago.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.iago.reminder.helpers.GlobalDialog
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.BottomNav
import com.iago.reminder.navigation.Navigation
import com.iago.reminder.ui.theme.ReminderTheme
import com.iago.reminder.utils.AlarmReceiver
import com.iago.reminder.utils.Constants.CHANNEL_ID
import com.iago.reminder.utils.Constants.CHANNEL_NAME
import com.iago.reminder.utils.GlobalDialogState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
        createNotificationChannel();
    }


    private fun createAlarm(word: Word) {
        val time = getTime(word.time)
        val intent = Intent(this@MainActivity, AlarmReceiver::class.java)
        intent.putExtra("word", word.word)
        intent.putExtra("translate", word.word_translate)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            AlarmManager.INTERVAL_DAY + time,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelAlarm(word: Word) {
        val intent = Intent(this@MainActivity, AlarmReceiver::class.java)
        intent.putExtra("word", word.word)
        intent.putExtra("translate", word.word_translate)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            word.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getTime(time: String): Long {
        val (hour, min) = time.split(":").map { it.toInt() }

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)

        return calendar.timeInMillis
    }

    @Composable
    fun App() {

        val navController = rememberNavController()
        val globalDialog = remember { mutableStateOf<GlobalDialogState?>(null) }

        ReminderTheme {

            if (globalDialog.value != null)
                GlobalDialog(
                    messageID = globalDialog.value!!.messageID,
                    error = globalDialog.value!!.error,
                    onDismiss = {
                        globalDialog.value!!.onDismiss()
                        globalDialog.value = null
                    },
                    onSuccess = {
                        globalDialog.value!!.onSuccess!!()
                        globalDialog.value = null
                    },
                    imageIconTwoOptions = globalDialog.value!!.imageIconTwoOptions

                )

            Scaffold(
                bottomBar = { BottomNav(navController = navController) }
            ) { paddingValues ->
                Navigation(
                    paddingBottom = paddingValues.calculateBottomPadding(),
                    navController = navController,
                    createAlarm = ::createAlarm,
                    cancelAlarm = ::cancelAlarm
                ) { globalDialog.value = it }
            }
        }
    }

}