package com.iago.reminder.screens.home.commons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.iago.reminder.R
import com.iago.reminder.ContextProvider
import com.iago.reminder.models.WordModel
import com.iago.reminder.theme.BackGroundDark
import com.iago.reminder.theme.Primary

@Composable
fun WordItem(wordModel: WordModel, index: Int, callback: (index: Int) -> Unit) {

    Chip(
        colors = ChipDefaults.chipColors(
            backgroundColor = BackGroundDark,
            contentColor = Primary
        ),
        onClick = { callback(index) },
        label = {
            Text(
                maxLines = 1,
                color = Color.White,
                text = wordModel.word,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Schedule,
                contentDescription = LocalContext.current.getString(R.string.desc_schedule_icon),
            )
        },
    )
}