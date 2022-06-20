package com.iago.reminder.screens.wordForm.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun TimeAndActiveContainer(remind: MutableState<Boolean>, time: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .background(
                color = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = 10.dp, horizontal = 20.dp),
    ) {
        RowTime(time)
        Divider(color = White, thickness = .5.dp)
        RowActive(remind)
    }
}