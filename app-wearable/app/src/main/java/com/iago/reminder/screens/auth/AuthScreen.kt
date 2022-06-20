package com.iago.reminder.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.iago.reminder.R
import com.iago.reminder.theme.BackGround

@Composable
fun AuthScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround),
        timeText = { TimeText() },
        vignette = { VignettePosition.TopAndBottom },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                tint = Color.White,
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Default.PhoneAndroid,
                contentDescription = "Phone unlock",
            )
            Text(
                color = Color.White,
                modifier = Modifier.padding(8.dp),
                text = LocalContext.current.getString(R.string.lbl_auth),
            )
        }
    }
}