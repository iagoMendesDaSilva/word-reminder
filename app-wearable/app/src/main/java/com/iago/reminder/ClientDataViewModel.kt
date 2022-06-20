package com.iago.reminder

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.wearable.*
import com.google.gson.Gson
import com.iago.reminder.models.TokenModel

class ClientDataViewModel(
    application: Application
) :
    AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {


    var token by mutableStateOf<String>("")
        private set


    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        DataLayerListenerService.AUTH_PATH -> {
                            val data =DataMapItem.fromDataItem(dataEvent.dataItem).dataMap.getString("token")
                            val tokenModel = Gson().fromJson(data, TokenModel::class.java)
                            token= tokenModel.token

                        }
                    }
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

    }

    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {

    }
}
