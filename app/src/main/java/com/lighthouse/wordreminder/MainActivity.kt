package com.lighthouse.wordreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.lighthouse.wordreminder.helpers.GlobalDialog
import com.lighthouse.wordreminder.navigation.BottomNav
import com.lighthouse.wordreminder.navigation.Navigation
import com.lighthouse.wordreminder.ui.theme.Background
import com.lighthouse.wordreminder.ui.theme.WordReminderTheme
import com.lighthouse.wordreminder.utils.Alarm
import com.lighthouse.wordreminder.utils.GlobalDialogState
import com.lighthouse.wordreminder.utils.Speaker

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Speaker.init(this)
        requestNotificationPermission()

        setContent {
            WordReminderTheme {
                WordReminderApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Speaker.shutdown()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @Composable
    private fun WordReminderApp() {
        val navController = rememberNavController()
        val globalDialog = remember { mutableStateOf<GlobalDialogState?>(null) }

        if (globalDialog.value != null) {
            GlobalDialog(
                dialogState = globalDialog.value!!.copy(
                    onDismiss = {
                        globalDialog.value?.onDismiss?.invoke()
                        globalDialog.value = null
                    }
                )
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Background,
            bottomBar = {
                BottomNav(navController = navController)
            }
        ) { innerPadding ->
            Navigation(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                navController = navController,
                createAlarm = { word, context -> Alarm.createAlarm(word, context) },
                cancelAlarm = { word, context -> Alarm.cancelAlarm(word, context) },
                openGlobalDialog = { globalDialog.value = it }
            )
        }
    }
}
