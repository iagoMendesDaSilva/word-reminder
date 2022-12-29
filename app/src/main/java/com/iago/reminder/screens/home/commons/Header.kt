package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun Header(
    import: () -> Unit,
    export: () -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.my_words),
            color = White,
            style = MaterialTheme.typography.h1
        )
        Row() {
            Icon(
                tint = White,
                contentDescription = null,
                imageVector = Icons.Default.Logout,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(30.dp)
                    .rotate(-90F)
                    .clickable {
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.exportWords,
                                imageIconTwoOptions = R.drawable.icon_warning,
                                onDismiss = {}, onSuccess = {
                                    export()
                                })
                        )
                    },
            )
            Icon(
                tint = White,
                contentDescription = null,
                imageVector = Icons.Default.Login,
                modifier = Modifier
                    .size(30.dp)
                    .rotate(90F)
                    .clickable {
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.importWords,
                                imageIconTwoOptions = R.drawable.icon_warning,
                                onDismiss = {}, onSuccess = {
                                    import()
                                })
                        )
                    },
            )
        }
    }
}