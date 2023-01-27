package com.iago.reminder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {
    object Home : BottomNavItem("My words", Icons.Default.LibraryBooks, Screens.HomeScreen.name)
    object Add : BottomNavItem("New word", Icons.Default.AddCircle, Screens.FormScreen.name)
    object Word : BottomNavItem("Practice", Icons.Default.Event, Screens.PracticeScreen.name)
}