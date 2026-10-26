package com.iago.reminder.screens.form.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.White10

@Composable
fun TimeAndActiveContainer(
    remind: MutableState<Boolean>,
    time: MutableState<String>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundDark),
        border = BorderStroke(1.dp, White10),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            RowTime(time = time)
            HorizontalDivider(color = White10, thickness = 1.dp)
            RowActive(remind = remind)
        }
    }
}
