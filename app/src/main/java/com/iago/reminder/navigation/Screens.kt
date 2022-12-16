package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    HomeScreen,
    SplashScreen,
    WordFormScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            SplashScreen.name -> SplashScreen
            WordFormScreen.name -> WordFormScreen
            null -> SplashScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}