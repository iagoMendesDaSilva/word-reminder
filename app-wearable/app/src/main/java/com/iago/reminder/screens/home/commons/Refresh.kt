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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.iago.reminder.R

@Composable
fun Refresh(refresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(50.dp)
                .clickable { refresh() },
            imageVector = Icons.Rounded.Refresh,
            contentDescription = stringResource(R.string.icon_reload),
        )
    }
}