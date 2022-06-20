package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White

@Composable
fun AddWord(modalVisible: MutableState<Boolean>) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            backgroundColor = Primary,
            onClick = { modalVisible.value = true }) {
            Icon(
                tint = White,
                imageVector = Icons.Default.Add,
                contentDescription =  stringResource(R.string.DESCRIPTION_ICON_ADD)
            )
        }
    }
}