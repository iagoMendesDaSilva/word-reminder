package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    GetStartedScreen,
    HomeScreen,
    LoginScreen,
    PasswordScreen,
    SplashScreen,
    VerificationCodeScreen,
    WelcomeScreen,
    WordFormScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            WelcomeScreen.name->WelcomeScreen
            SplashScreen.name -> SplashScreen
            PasswordScreen.name->PasswordScreen
            WordFormScreen.name -> WordFormScreen
            GetStartedScreen.name->GetStartedScreen
            VerificationCodeScreen.name -> VerificationCodeScreen
            null -> WelcomeScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}