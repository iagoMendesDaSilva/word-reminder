package com.iago.reminder.navigation

import com.iago.reminder.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("My words", R.drawable.ic_list, Screens.HomeScreen.name)
    object Word : BottomNavItem("Discover new word", R.drawable.ic_calendar, Screens.VocabularyScreen.name)
}