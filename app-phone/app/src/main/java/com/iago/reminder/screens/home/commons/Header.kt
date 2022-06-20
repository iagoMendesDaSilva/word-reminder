package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun Header(
    connected: Boolean,
    viewModel: HomeScreenViewModel,
    sendToken: (token: String) -> Unit,
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    var expanded = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.my_words),
            color = White,
            style = MaterialTheme.typography.h1
        )
        Box {
            IconButton(onClick = { expanded.value = true }) {
                Icon(Icons.Default.MoreVert, tint = White, contentDescription = null)
            }
            DropDownMenuHeader(
                expanded,
                connected,
                viewModel,
                sendToken,
                navController,
                openGlobalDialog
            )
        }
    }
}