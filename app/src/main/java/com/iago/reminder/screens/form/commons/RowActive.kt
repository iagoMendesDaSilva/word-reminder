package com.iago.reminder.screens.form.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.helpers.SwitchDefault
import com.iago.reminder.ui.theme.White

@Composable
fun RowActive(remind: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row() {
            Icon(
                modifier = Modifier.padding(end = 5.dp),
                tint = White,
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.reminde_me),
                color = White,
                style = MaterialTheme.typography.body1
            )
        }
        SwitchDefault(remind.value) {
            remind.value = it
        }
    }
}