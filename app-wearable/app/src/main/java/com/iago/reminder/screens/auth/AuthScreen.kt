package com.iago.reminder.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.iago.reminder.R
import com.iago.reminder.theme.White

@Composable
fun AuthScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
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
                tint = White,
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Default.PhoneAndroid,
                contentDescription = stringResource(R.string.icon_phone),
            )
            Text(
                color = White,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(R.string.auth),
            )
        }
    }
}