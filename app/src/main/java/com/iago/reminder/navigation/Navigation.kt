package com.iago.reminder.navigation

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.iago.reminder.models.Word
import com.iago.reminder.screens.form.FormScreen
import com.iago.reminder.screens.home.HomeScreen
import com.iago.reminder.screens.practice.PracticeScreen
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.HomeScreen.name,
        enterTransition = { fadeIn(animationSpec = tween(280)) },
        exitTransition = { fadeOut(animationSpec = tween(280)) },
        popEnterTransition = { fadeIn(animationSpec = tween(280)) },
        popExitTransition = { fadeOut(animationSpec = tween(280)) }
    ) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                createAlarm = createAlarm,
                cancelAlarm = cancelAlarm,
                openGlobalDialog = openGlobalDialog
            )
        }

        composable(Screens.PracticeScreen.name) {
            PracticeScreen(
                navController = navController,
                openGlobalDialog = openGlobalDialog
            )
        }

        composable(
            route = "${Screens.FormScreen.name}?wordId={wordId}",
            arguments = listOf(
                navArgument("wordId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val wordId = backStackEntry.arguments?.getInt("wordId") ?: -1
            FormScreen(
                navController = navController,
                wordId = if (wordId > 0) wordId else null,
                createAlarm = createAlarm,
                cancelAlarm = cancelAlarm,
                openGlobalDialog = openGlobalDialog
            )
        }
    }
}
