package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Watch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState
import com.onesignal.OneSignal
import kotlinx.coroutines.launch


@Composable
fun DropDownMenuHeader(
    expanded: MutableState<Boolean>,
    connected: Boolean,
    viewModel: HomeScreenViewModel,
    sendToken: (token: String) -> Unit,
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    DropdownMenu(
        modifier = Modifier.background(color = MaterialTheme.colors.onBackground),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        if (connected)
            DropdownMenuItem(onClick = {
                coroutineScope.launch {
                    sendTokenWatch(viewModel, sendToken, expanded)
                }
            }) {
                Row(modifier = Modifier.width(170.dp)) {
                    Icon(
                        tint = White,
                        contentDescription = null,
                        imageVector = Icons.Default.Watch,
                    )
                    Text(
                        color = White,
                        text = stringResource(R.string.button_sign_in),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        DropdownMenuItem(
            onClick = {
                openGlobalDialog(GlobalDialogState(R.string.logout,
                    imageIconTwoOptions = R.drawable.icon_logout,
                    onDismiss = {
                        expanded.value = false
                    }, onSuccess = {
                        logout(viewModel, navController, expanded)
                    })
                )

            }) {
            Row(modifier = Modifier.width(170.dp)) {
                Icon(
                    tint = White,
                    contentDescription = null,
                    imageVector = Icons.Default.PowerSettingsNew,
                )
                Text(
                    text = stringResource(R.string.button_logout),
                    color = White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
        DropdownMenuItem(
            onClick = {
                openGlobalDialog(
                    GlobalDialogState(R.string.unregister,
                        imageIconTwoOptions = R.drawable.icon_warning,
                        onDismiss = { expanded.value = false },
                        onSuccess = {
                            unregister(viewModel, navController, expanded)
                        })
                )

            }) {
            Row(modifier = Modifier.width(170.dp)) {
                Icon(
                    tint = White,
                    contentDescription = null,
                    imageVector = Icons.Default.Delete,
                )
                Text(
                    color = White,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.button_unregister),
                )
            }
        }
    }
}

suspend fun sendTokenWatch(
    viewModel: HomeScreenViewModel,
    sendToken: (token: String) -> Unit,
    expanded: MutableState<Boolean>
) {
    val token = viewModel.getToken()
    if (token != null)
        sendToken(token)
    expanded.value = false

}

fun unregister(
    viewModel: HomeScreenViewModel,
    navController: NavHostController,
    expanded: MutableState<Boolean>
) {
    viewModel.unregister() { resetAppToWelcome(viewModel, navController) }
    expanded.value = false
}

fun logout(
    viewModel: HomeScreenViewModel,
    navController: NavHostController,
    expanded: MutableState<Boolean>
) {
    viewModel.logout() { resetAppToWelcome(viewModel, navController) }
    expanded.value = false
}

fun resetAppToWelcome(viewModel: HomeScreenViewModel, navController: NavHostController) {
    viewModel.clearDB()
    OneSignal.disablePush(true)
    navController.navigate(Screens.WelcomeScreen.name) {
        popUpTo(0)
        launchSingleTop = true
    }
}
