package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    HomeScreen,
    SplashScreen,
    PracticeScreen,
    FormScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            SplashScreen.name->SplashScreen
            FormScreen.name -> FormScreen
            PracticeScreen.name -> PracticeScreen
            null -> SplashScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}