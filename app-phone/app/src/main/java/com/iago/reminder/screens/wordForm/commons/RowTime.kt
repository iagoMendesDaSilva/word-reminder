package com.iago.reminder.screens.wordForm.commons

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RowTime(time: MutableState<String>) {

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.SECOND, 0)
    val simpleDateFormat = SimpleDateFormat("HH:mm aa");

    val mTimePickerDialog = TimePickerDialog(
        LocalContext.current, { _, hour: Int, minute: Int ->
            run {
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                time.value = simpleDateFormat.format(calendar.time)
            }
        }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                mTimePickerDialog.show()
            }
            .padding(bottom = 10.dp, top = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row() {
            Icon(
                tint = White,
                imageVector = Icons.Default.Schedule,
                modifier = Modifier.padding(end = 5.dp),
                contentDescription = null,
            )
            Text(text = time.value.substring(0, 5), color = White, style = MaterialTheme.typography.body1)
        }
        Text(text = time.value.substring(5, 8), color = White, style = MaterialTheme.typography.body1)
    }
}