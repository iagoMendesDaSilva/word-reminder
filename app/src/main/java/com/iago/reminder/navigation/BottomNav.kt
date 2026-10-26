package com.iago.reminder.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White50

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.Practice,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isTopLevelRoute = currentRoute in items.map { it.screenRoute } ||
            currentRoute?.startsWith(Screens.FormScreen.name) == true

    if (isTopLevelRoute) {
        NavigationBar(
            containerColor = BackgroundDark,
            tonalElevation = 8.dp
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.screenRoute ||
                        (item == BottomNavItem.Add && currentRoute?.startsWith(Screens.FormScreen.name) == true)

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            if (item.screenRoute == Screens.HomeScreen.name) {
                                navController.popBackStack(Screens.HomeScreen.name, false)
                            } else {
                                navController.navigate(item.screenRoute) {
                                    popUpTo(Screens.HomeScreen.name) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = White,
                        selectedTextColor = White,
                        indicatorColor = Primary.copy(alpha = 0.35f),
                        unselectedIconColor = White50,
                        unselectedTextColor = White50
                    )
                )
            }
        }
    }
}
