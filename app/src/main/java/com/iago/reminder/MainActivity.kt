package com.iago.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.iago.reminder.navigation.BottomNav
import com.iago.reminder.navigation.Navigation
import com.iago.reminder.ui.theme.ReminderTheme
import com.iago.reminder.utils.Alarm
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
                    createAlarm = { word, context -> Alarm.createAlarm(word, context) },
                    cancelAlarm = { word, context -> Alarm.cancelAlarm(word, context) },
                ) { globalDialog.value = it }
            }
        }
    }

}