package com.iago.reminder.screens.home.commons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.*
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.theme.Gray
import com.iago.reminder.theme.White

@Composable
fun WordItem(wordModel: WordModel, index: Int, callback: (index: Int) -> Unit) {

    Chip(
        colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colors.onBackground,
            contentColor = MaterialTheme.colors.primary
        ),
        onClick = { callback(index) },
        label = {
            Text(
                maxLines = 1,
                color = White,
                text = wordModel.word,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.button,
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Schedule,
                contentDescription = stringResource(R.string.icon_clock),
                tint = if (wordModel.active) MaterialTheme.colors.primary else Gray,
            )
        },
    )
}