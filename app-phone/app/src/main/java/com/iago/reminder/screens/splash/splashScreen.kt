package com.iago.reminder.screens.splash

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.BuildConfig
import com.iago.reminder.R
import com.iago.reminder.helpers.Logo
import com.iago.reminder.helpers.MeditationImage
import com.iago.reminder.navigation.Screens
import com.iago.reminder.utils.GlobalDialogState
import com.onesignal.OneSignal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<SplashViewModel>()
    val coroutineScope = rememberCoroutineScope()

    var error = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = true, block = {
        configOnesignal(context)
        verifyUser(navController, viewModel, error)
    })

    if (error.value != null)
        openGlobalDialog(GlobalDialogState(error.value!!) {
            error.value = null
            coroutineScope.launch {
                onError(navController, viewModel)
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MeditationImage()
        Spacer(modifier = Modifier.height(25.dp))
        Logo()
    }
}

fun configOnesignal(context: Context) {
    OneSignal.initWithContext(context)
    OneSignal.setAppId(BuildConfig.ONESIGNAL_APP_ID)
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
}

suspend fun verifyUser(
    navController: NavHostController,
    viewModel: SplashViewModel,
    error: MutableState<Int?>
) {
    delay(1500L)
    val user = viewModel.verifyUser()
    if (user != null) {
        viewModel.login(user.email, user.password!!) {
            if (it)
                navController.navigate(Screens.HomeScreen.name)
            else
                error.value = R.string.error_session
        }
    } else onError(navController, viewModel)


}

suspend fun onError(
    navController: NavHostController,
    viewModel: SplashViewModel,
) {
    viewModel.onErrorRefresh()
    navController.navigate(Screens.WelcomeScreen.name)
}
