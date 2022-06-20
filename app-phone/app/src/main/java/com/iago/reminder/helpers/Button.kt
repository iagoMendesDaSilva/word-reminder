package com.iago.reminder.screens.login.commons

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.screens.login.LoginViewModel
import com.iago.reminder.ui.theme.White

@Composable
fun ButtonDefault(text:String, loading: Boolean, navController: NavHostController, callback: () -> Unit) {

    val focusManager = LocalFocusManager.current


    Button(
        onClick = {
            focusManager.clearFocus()
            callback()
        },
        modifier = Modifier
            .padding(8.dp)
            .height(50.dp)
            .fillMaxWidth(.7f),

        shape = RoundedCornerShape(40.dp)
    ) {
        if (loading)
            Load()
        else
            Text(text = text, color = White)
    }
}

@Composable
fun Load() {
    CircularProgressIndicator(
        color = White,
        strokeWidth = 3.dp,
        modifier = Modifier.size(25.dp)
    )
}