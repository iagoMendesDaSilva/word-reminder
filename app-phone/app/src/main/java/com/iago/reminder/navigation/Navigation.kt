package com.iago.reminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.getStarted.GetStartedScreen
import com.iago.reminder.screens.home.HomeScreen
import com.iago.reminder.screens.login.LoginScreen
import com.iago.reminder.screens.password.PasswordScreen
import com.iago.reminder.screens.splash.SplashScreen
import com.iago.reminder.screens.verificationCode.VerificationCodeScreen
import com.iago.reminder.screens.welcome.WelcomeScreen
import com.iago.reminder.screens.wordForm.WordFormScreen
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun Navigation(
    connected: Boolean,
    sendToken: (token: String) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.name) {

        composable(
            Screens.GetStartedScreen.name + "/{register}",
            arguments = listOf(navArgument("register") { type = NavType.BoolType })
        ) {
            GetStartedScreen(
                navController,
                it.arguments?.getBoolean("register")!!,
                openGlobalDialog
            )
        }

        composable(Screens.HomeScreen.name) {
            HomeScreen(navController, connected, sendToken, openGlobalDialog)
        }

        composable(Screens.LoginScreen.name) {
            LoginScreen(navController, openGlobalDialog)
        }

        composable(
            Screens.PasswordScreen.name + "/{email}" + "?token={token}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("token") { defaultValue = "" }
            )
        ) {
            PasswordScreen(
                navController,
                it.arguments?.getString("email")!!,
                it.arguments?.getString("token")!!,
                openGlobalDialog
            )
        }

        composable(Screens.SplashScreen.name) {
            SplashScreen(navController, openGlobalDialog)
        }

        composable(
            Screens.VerificationCodeScreen.name + "/{email}" + "/{id}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            VerificationCodeScreen(
                navController,
                it.arguments?.getString("email")!!,
                it.arguments?.getInt("id")!!,
                openGlobalDialog
            )
        }

        composable(Screens.WelcomeScreen.name) {
            WelcomeScreen(navController, openGlobalDialog)
        }

        composable(
            Screens.WordFormScreen.name + "?word={word}"
        ) {
            val word = navController.previousBackStackEntry?.arguments?.getParcelable<WordModel>("word")
            WordFormScreen(
                navController,
                word,
                openGlobalDialog
            )
        }

    }
}