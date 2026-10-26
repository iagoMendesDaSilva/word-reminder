package com.iago.reminder.screens.form.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White70
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowTime(
    time: MutableState<String>,
    modifier: Modifier = Modifier
) {
    val showPicker = remember { mutableStateOf(false) }

    val initialHour = time.value.split(":").getOrNull(0)?.toIntOrNull() ?: 8
    val initialMinute = time.value.split(":").getOrNull(1)?.take(2)?.toIntOrNull() ?: 30

    if (showPicker.value) {
        val timePickerState = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = false
        )

        AlertDialog(
            onDismissRequest = { showPicker.value = false },
            containerColor = BackgroundDark,
            shape = RoundedCornerShape(28.dp),
            confirmButton = {
                TextButton(
                    onClick = {
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            set(Calendar.MINUTE, timePickerState.minute)
                            set(Calendar.SECOND, 0)
                        }
                        val sdf = SimpleDateFormat("HH:mm aa", Locale.US)
                        time.value = sdf.format(calendar.time)
                        showPicker.value = false
                    }
                ) {
                    Text(stringResource(R.string.ok), color = Primary, style = MaterialTheme.typography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker.value = false }) {
                    Text(stringResource(R.string.button_cancel), color = White70, style = MaterialTheme.typography.labelLarge)
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = BackgroundDark,
                            selectorColor = Primary,
                            containerColor = BackgroundDark,
                            periodSelectorBorderColor = Primary,
                            periodSelectorSelectedContainerColor = Primary,
                            periodSelectorUnselectedContainerColor = BackgroundDark,
                            periodSelectorSelectedContentColor = White,
                            periodSelectorUnselectedContentColor = White70,
                            timeSelectorSelectedContainerColor = Primary,
                            timeSelectorUnselectedContainerColor = BackgroundDark,
                            timeSelectorSelectedContentColor = White,
                            timeSelectorUnselectedContentColor = White70
                        )
                    )
                }
            }
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showPicker.value = true }
            .padding(bottom = 12.dp, top = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Schedule,
                contentDescription = null,
                tint = White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = time.value.take(5),
                color = White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        val amPm = if (time.value.length > 5) time.value.substring(5).trim() else "AM"
        Text(
            text = amPm.uppercase(Locale.getDefault()),
            color = White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
