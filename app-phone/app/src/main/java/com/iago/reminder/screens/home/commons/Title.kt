package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Watch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.home.HomeViewModel
import com.iago.reminder.ui.theme.White
import com.onesignal.OneSignal

@Composable
fun Title(
    navController: NavHostController,
    connected: Boolean,
    sendToken: (token: String) -> Unit
) {

    val homeViewModel = hiltViewModel<HomeViewModel>()

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            color = White,
            fontSize = 25.sp,
            text = stringResource(R.string.LBL_TITLE),
            modifier = Modifier.padding(10.dp)
        )
        Row() {
            IconButton(onClick = { logout(homeViewModel, navController) }) {
                Icon(
                    tint = White,
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(R.string.DESCRIPTION_ICON_LOGOUT),
                )
            }
            if (connected)
                IconButton(onClick = { homeViewModel.getToken(sendToken) }) {
                    Icon(
                        tint = White,
                        imageVector = Icons.Default.Watch,
                        contentDescription = stringResource(R.string.DESCRIPTION_ICON_WATCH),
                    )
                }
        }
    }
}


fun logout(homeViewModel: HomeViewModel, navController: NavHostController) {
    homeViewModel.logout() {
        homeViewModel.clearDB()
        OneSignal.disablePush(true)
        navController.navigate(Screens.LoginScreen.name) {
            popUpTo(0)
            launchSingleTop = true
        }
    }
}