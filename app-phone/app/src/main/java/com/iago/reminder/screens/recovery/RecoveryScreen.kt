package com.iago.reminder.screens.recovery

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.helpers.Logo
import com.iago.reminder.screens.login.commons.ButtonDefault
import com.iago.reminder.screens.recovery.commons.InputCode

@Composable
fun RecoveryScreen(navController: NavHostController, id: Int, email: String) {

    val context = LocalContext.current
    val recoveryViewModel = hiltViewModel<RecoveryViewModel>()

    var code01 by remember { mutableStateOf("") }
    var code02 by remember { mutableStateOf("") }
    var code03 by remember { mutableStateOf("") }
    var code04 by remember { mutableStateOf("") }
    var code05 by remember { mutableStateOf("") }

    LaunchedEffect(key1 = recoveryViewModel.error.value) {
        if (!recoveryViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, recoveryViewModel.error.value, Toast.LENGTH_SHORT).show()
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InputCode(code01) { code01 = it }
            InputCode(code02) { code02 = it }
            InputCode(code03) { code03 = it }
            InputCode(code04) { code04 = it }
            InputCode(code05) { code05 = it }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ButtonDefault(stringResource(R.string.BTN_NEXT), recoveryViewModel.loading.value, navController) {
            recovery(
                code01,
                code02,
                code03,
                code04,
                code05,
                email,
                recoveryViewModel,
                navController,
                id,
                context
            )
        }
    }
}

fun recovery(
    code01: String,
    code02: String,
    code03: String,
    code04: String,
    code05: String,
    email: String,
    recoveryViewModel: RecoveryViewModel,
    navController: NavHostController,
    id: Int,
    context: Context
) {
    if (code01.isNullOrEmpty() || code02.isNullOrEmpty() || code03.isNullOrEmpty() || code04.isNullOrEmpty() || code05.isNullOrEmpty())
        Toast.makeText(context,  ContextProvider.getContext().getString(R.string.ERROR_EMPTY), Toast.LENGTH_SHORT).show()
    else
        recoveryViewModel.confirmCode(
            email,
            id,
            "${code01}${code02}${code03}${code04}${code05}",
            navController
        )
}