package com.iago.reminder.nav


enum class Screens {
    AuthScreen,
    HomeScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            AuthScreen.name -> AuthScreen
            null -> AuthScreen
            else -> throw  IllegalArgumentException("Route $route is not recognized")
        }
    }

}