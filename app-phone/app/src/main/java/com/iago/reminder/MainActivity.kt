package com.iago.reminder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.iago.reminder.models.TokenModel
import com.iago.reminder.models.TokenWearableModel
import com.iago.reminder.navigation.Navigation
import com.iago.reminder.ui.theme.ReminderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity(),  GoogleApiClient.ConnectionCallbacks {

    private lateinit var client: GoogleApiClient
    private var connected by mutableStateOf(false)
    private val dataClient by lazy { Wearable.getDataClient(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderTheme {
                Navigation(connected, sendToken = ::sendToken)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        verifyWearableConnected()
    }

    override fun onPause() {
        super.onPause()
        connected=false
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
                val tokenData = Gson().toJson(TokenWearableModel(token, System.currentTimeMillis().toString()))
                val request = PutDataMapRequest.create("/auth").apply {
                    dataMap.putString("token", tokenData)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Toast.makeText(
                    this@MainActivity,
                    this@MainActivity.getString(R.string.SUCCESS_WATCH),
                    Toast.LENGTH_SHORT
                ).show()
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    this@MainActivity.getString(R.string.ERROR_WATCH),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback {
           connected = it.nodes.isNotEmpty()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        connected= false
    }
}