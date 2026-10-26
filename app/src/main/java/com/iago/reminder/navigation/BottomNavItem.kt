package com.iago.reminder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, val screenRoute: String) {
    data object Home : BottomNavItem("My words", Icons.AutoMirrored.Rounded.MenuBook, Screens.HomeScreen.name)
    data object Add : BottomNavItem("New word", Icons.Rounded.AddCircle, Screens.FormScreen.name)
    data object Practice : BottomNavItem("Practice", Icons.Rounded.Psychology, Screens.PracticeScreen.name)
}
