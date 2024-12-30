package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.Error
import com.iago.reminder.ui.theme.White

@Composable
fun WordItemSwipedEnd(id: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Error)
            .padding(20.dp),
    ) {
        Icon(
            tint = White,
            contentDescription = null,
            imageVector = Icons.Default.Delete,
            modifier = Modifier.align(Alignment.CenterEnd).size(40.dp),
        )
    }
}