package com.iago.reminder.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iago.reminder.ui.theme.BackGroundDark
import com.iago.reminder.ui.theme.Gray
import com.iago.reminder.ui.theme.White

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Word,
    )
           val navBackStackEntry by navController.currentBackStackEntryAsState()
           val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in items.map { it.screen_route }
   if(showBottomBar)
       BottomNavigation(
           backgroundColor = BackGroundDark
       ) {
           items.forEach { item ->
               BottomNavigationItem(
                   icon = {
                       Icon(
                           painterResource(id = item.icon),
                           contentDescription = item.title,
                       )
                   },
                   label = {
                       Text(
                           fontSize = 15.sp,
                           text = item.title,
                       )
                   },
                   selectedContentColor = White,
                   unselectedContentColor = Gray,
                   alwaysShowLabel = false,
                   selected = currentRoute == item.screen_route,
                   onClick = {
                       navController.navigate(item.screen_route) {

                           navController.graph.startDestinationRoute?.let { screen_route ->
                               popUpTo(screen_route) {
                                   saveState = true
                               }
                           }
                           launchSingleTop = true
                           restoreState = true
                       }
                   }
               )
           }
       }
}
