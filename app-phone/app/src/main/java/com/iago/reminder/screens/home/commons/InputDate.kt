package com.iago.reminder.screens.home.commons

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun InputDate(dateInput: MutableState<String>) {

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.SECOND, 0)

    val simpleDateFormat = SimpleDateFormat("HH:mm");

    if (dateInput.value.isNullOrEmpty())
        dateInput.value = simpleDateFormat.format(calendar.time)
    else
        dateInput.value = dateInput.value.substring(0, 5)

    val mTimePickerDialog = TimePickerDialog(
        LocalContext.current, { _, hour: Int, minute: Int ->
            run {
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                dateInput.value = simpleDateFormat.format(calendar.time)
            }
        }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true
    )

    Text(
        color = White,
        text = dateInput.value,
        modifier = Modifier
            .padding(start = 5.dp, top = 5.dp)
            .clickable { mTimePickerDialog.show() })
}