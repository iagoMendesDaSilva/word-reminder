package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    LoginScreen,
    SplashScreen,
    RegisterScreen,
    PasswordScreen,
    WarningScreen,
    RecoveryScreen,
    ForgotPasswordScreen,
    HomeScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            WarningScreen.name->WarningScreen
            RegisterScreen.name -> RegisterScreen
            PasswordScreen.name->PasswordScreen
            RecoveryScreen.name->RecoveryScreen
            SplashScreen.name -> SplashScreen
            ForgotPasswordScreen.name -> ForgotPasswordScreen
            null -> LoginScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}