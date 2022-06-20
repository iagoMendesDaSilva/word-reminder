package com.iago.reminder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.iago.reminder.helpers.GlobalDialog
import com.iago.reminder.models.TokenWearableModel
import com.iago.reminder.navigation.Navigation
import com.iago.reminder.ui.theme.ReminderTheme
import com.iago.reminder.utils.GlobalDialogState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity(), GoogleApiClient.ConnectionCallbacks {

    private lateinit var client: GoogleApiClient
    private var connected by mutableStateOf(false)
    private val dataClient by lazy { Wearable.getDataClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun App() {
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

            Navigation(
                connected = connected,
                sendToken = ::sendToken,
                openGlobalDialog = { globalDialog.value = it }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        verifyWearableConnected()
    }

    override fun onPause() {
        super.onPause()
        connected = false
    }

    private fun verifyWearableConnected() {
        client = GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .addConnectionCallbacks(this)
            .build()
        client.connect()

    }

    private fun sendToken(token: String) {
        lifecycleScope.launch {
            try {
                val tokenData =
                    Gson().toJson(TokenWearableModel(token, System.currentTimeMillis().toString()))
                val request = PutDataMapRequest.create("/auth").apply {
                    dataMap.putString("token", tokenData)
                }
                    .asPutDataRequest()
                    .setUrgent()

                dataClient.putDataItem(request).await()
            } catch (exception: Exception) {
                Log.d("dataLayer", exception.localizedMessage)
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback {
            connected = it.nodes.isNotEmpty()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        connected = false
    }
}