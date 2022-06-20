package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import com.iago.reminder.R
import com.iago.reminder.ContextProvider
import com.iago.reminder.theme.Primary

@Composable
fun Refresh(refresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            tint = Primary,
            modifier = Modifier
                .size(50.dp)
                .clickable { refresh() },
            imageVector = Icons.Rounded.Refresh,
            contentDescription = LocalContext.current.getString(R.string.desc_reload_icon),
        )
    }
}