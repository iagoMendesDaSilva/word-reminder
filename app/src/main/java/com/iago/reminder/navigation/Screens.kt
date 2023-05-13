package com.iago.reminder.navigation

import java.lang.IllegalArgumentException

enum class Screens {
    HomeScreen,
    PracticeScreen,
    FormScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FormScreen.name -> FormScreen
            PracticeScreen.name -> PracticeScreen
            null -> HomeScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}