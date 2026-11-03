package com.lighthouse.wordreminder.navigation

enum class Screens {
    HomeScreen,
    PracticeScreen,
    FormScreen,
    AboutScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FormScreen.name -> FormScreen
            PracticeScreen.name -> PracticeScreen
            AboutScreen.name -> AboutScreen
            null -> HomeScreen
            else -> HomeScreen
        }
    }
}
