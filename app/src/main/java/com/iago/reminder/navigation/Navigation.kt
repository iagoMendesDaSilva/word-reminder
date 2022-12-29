package com.iago.reminder.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iago.reminder.models.Word
import com.iago.reminder.screens.form.FormScreen
import com.iago.reminder.screens.home.HomeScreen
import com.iago.reminder.screens.splash.SplashScreen
import com.iago.reminder.screens.vocabulary.VocabularyScreen
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun Navigation(
    paddingBottom: Dp,
    navController: NavHostController,
    createAlarm: (word: Word) -> Unit,
    cancelAlarm: (word: Word) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    NavHost(
        modifier = Modifier.padding(bottom = paddingBottom),
        navController = navController,
        startDestination = Screens.SplashScreen.name
    ) {

        composable(Screens.SplashScreen.name) {
            SplashScreen(navController, openGlobalDialog)
        }

        composable(Screens.HomeScreen.name) {
            HomeScreen(navController, createAlarm, cancelAlarm, openGlobalDialog)
        }

        composable(Screens.VocabularyScreen.name) {
            VocabularyScreen(navController, openGlobalDialog)
        }

        composable(
            Screens.FormScreen.name + "?word={word}"
        ) {
            val word =
                navController.previousBackStackEntry?.arguments?.getParcelable<Word>("word")
            FormScreen(
                navController,
                word,
                createAlarm,
                cancelAlarm,
                openGlobalDialog
            )
        }

    }
}