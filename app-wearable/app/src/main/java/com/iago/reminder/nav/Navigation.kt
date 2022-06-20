package com.iago.reminder.nav

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.iago.reminder.screens.auth.AuthScreen
import com.iago.reminder.screens.home.HomeScreen


@Composable
fun Navigation(token: String) {
    val navController = rememberSwipeDismissableNavController()

    if (!token.isNullOrEmpty())
        navController.navigate(Screens.HomeScreen.name) {
            popUpTo(0)
            launchSingleTop = true
        }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screens.AuthScreen.name,
    ) {
        composable(Screens.AuthScreen.name) {
            AuthScreen()
        }
        composable(Screens.HomeScreen.name) {
            HomeScreen(token)
        }
    }
}