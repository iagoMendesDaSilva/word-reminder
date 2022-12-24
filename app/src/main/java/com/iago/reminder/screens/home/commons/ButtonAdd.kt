package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.navigation.Screens
import com.iago.reminder.ui.theme.White

@Composable
fun ButtonAdd(navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(25.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                navController.currentBackStackEntry?.arguments?.putParcelable("word", null)
                navController.navigate(Screens.FormScreen.name)
            }) {
            Icon(
                tint = White,
                contentDescription = null,
                imageVector = Icons.Default.Add,
            )
        }
    }
}