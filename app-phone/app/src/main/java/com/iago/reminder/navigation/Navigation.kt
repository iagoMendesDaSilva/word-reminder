package com.iago.reminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iago.reminder.screens.forgotPassword.ForgotPasswordScreen
import com.iago.reminder.screens.register.RegisterScreen
import com.iago.reminder.screens.home.HomeScreen
import com.iago.reminder.screens.login.LoginScreen
import com.iago.reminder.screens.password.PasswordScreen
import com.iago.reminder.screens.recovery.RecoveryScreen
import com.iago.reminder.screens.splash.SplashScreen
import com.iago.reminder.screens.warning.WarningScreen

@Composable
fun Navigation(connected: Boolean, sendToken: (token: String) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.name) {

        composable(Screens.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(Screens.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(Screens.HomeScreen.name) {
            HomeScreen(navController, connected, sendToken)
        }

        composable(Screens.RegisterScreen.name) {
            RegisterScreen(navController)
        }

        composable(
            Screens.PasswordScreen.name + "/{email}/{token}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("token") { NavType.StringType })

        ) {
            PasswordScreen(
                navController,
                it.arguments?.getString("email")!!,
                it.arguments?.getString("token")!!
            )
        }

        composable(Screens.WarningScreen.name + "/{warning}",
            arguments = listOf(navArgument("warning") { type = NavType.StringType })
        ) {
            WarningScreen(navController, it.arguments?.getString("warning")!!)
        }

        composable(Screens.ForgotPasswordScreen.name) {
            ForgotPasswordScreen(navController)
        }

        composable(
            Screens.RecoveryScreen.name + "/{email}/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("email") { type = NavType.StringType })
        ) {
            RecoveryScreen(
                navController,
                it.arguments?.getInt("id")!!,
                it.arguments?.getString("email")!!
            )
        }

    }
}