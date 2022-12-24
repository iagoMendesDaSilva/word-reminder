package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    HomeScreen,
    SplashScreen,
    VocabularyScreen,
    FormScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            SplashScreen.name->SplashScreen
            FormScreen.name -> FormScreen
            VocabularyScreen.name -> VocabularyScreen
            null -> SplashScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}