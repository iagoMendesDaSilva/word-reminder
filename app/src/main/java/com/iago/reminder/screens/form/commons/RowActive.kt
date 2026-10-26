package com.iago.reminder.screens.form.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun RowActive(
    remind: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = null,
                tint = White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.reminde_me),
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )
        }

        SwitchDefault(initialValue = remind.value) { checked ->
            remind.value = checked
        }
    }
}
