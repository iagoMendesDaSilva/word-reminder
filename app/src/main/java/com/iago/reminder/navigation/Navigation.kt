package com.iago.reminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.HomeScreen
import com.iago.reminder.screens.splash.SplashScreen
import com.iago.reminder.screens.wordForm.WordFormScreen
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun Navigation(
    createAlarm:(word:WordModel)->Unit,
    cancelAlarm:(word:WordModel)->Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.name) {

        composable(Screens.HomeScreen.name) {
            HomeScreen(navController, createAlarm, cancelAlarm, openGlobalDialog)
        }

        composable(Screens.SplashScreen.name) {
            SplashScreen(navController, openGlobalDialog)
        }

        composable(
            Screens.WordFormScreen.name + "?word={word}"
        ) {
            val word = navController.previousBackStackEntry?.arguments?.getParcelable<WordModel>("word")
            WordFormScreen(
                navController,
                word,
                createAlarm,
                cancelAlarm,
                openGlobalDialog
            )
        }

    }
}