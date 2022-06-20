package com.iago.reminder.screens.verificationCode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.verificationCode.commons.InputCode
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun VerificationCodeScreen(
    navController: NavHostController,
    email: String,
    id: Int,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    var code1 = remember { mutableStateOf("") }
    var code2 = remember { mutableStateOf("") }
    var code3 = remember { mutableStateOf("") }
    var code4 = remember { mutableStateOf("") }
    var code5 = remember { mutableStateOf("") }

    val viewModel = hiltViewModel<VerificationCodeViewModel>()

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = code1.value) {
        moveFocus(code1.value, focusManager)
    }

    LaunchedEffect(key1 = code2.value) {
        moveFocus(code2.value, focusManager)
    }

    LaunchedEffect(key1 = code3.value) {
        moveFocus(code3.value, focusManager)
    }

    LaunchedEffect(key1 = code4.value) {
        moveFocus(code4.value, focusManager)
    }

    LaunchedEffect(key1 = code5.value) {
        moveFocus(code5.value, focusManager, last = true)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleContainer()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            InputCode(value = code1.value) { code1.value = it }
            InputCode(value = code2.value) { code2.value = it }
            InputCode(value = code3.value) { code3.value = it }
            InputCode(value = code4.value) { code4.value = it }
            InputCode(value = code5.value) { code5.value = it }
        }
        ButtonDefault(stringResource(R.string.button_submit), viewModel.loading.value) {
            onClickButton(
                viewModel,
                code1.value,
                code2.value,
                code3.value,
                code4.value,
                code5.value,
                navController,
                email,
                id
            )
        }
    }
}

@Composable
fun TitleContainer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.verification_code),
            color = White,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = stringResource(R.string.verification_code_desc),
            color = White,
            style = MaterialTheme.typography.body1
        )
    }
}

fun moveFocus(
    value: String,
    focusManager: FocusManager,
    last: Boolean = false
) {
    if (value.isNotEmpty() && !last)
        focusManager.moveFocus(focusDirection = FocusDirection.Next)
}

fun onClickButton(
    viewModel: VerificationCodeViewModel,
    code1: String,
    code2: String,
    code3: String,
    code4: String,
    code5: String,
    navController: NavHostController,
    email: String,
    id: Int,
) {
    if (code1.isNullOrEmpty() || code2.isNullOrEmpty() || code3.isNullOrEmpty() || code4.isNullOrEmpty() || code5.isNullOrEmpty())
        viewModel.error.value = R.string.error_empty_code
    else
        viewModel.confirmCode(id, "${code1}${code2}${code3}${code4}${code5}") { token ->
            navController.navigate(Screens.PasswordScreen.name + "/${email}" + "?token=${token}")
        }

}
