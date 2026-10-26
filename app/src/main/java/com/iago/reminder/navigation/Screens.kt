package com.iago.reminder.navigation

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
            else -> HomeScreen
        }
    }
}
