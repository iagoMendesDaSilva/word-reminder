package com.iago.reminder.screens.password.commons

import android.content.res.Resources
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.ui.theme.BackGroundDark
import com.iago.reminder.ui.theme.Primary

@Composable
fun ProgressPassword(indicatorProgress: Float) {
    var progress by remember { mutableStateOf(0f) }

    val progressAnimDuration = 1500
    val progressAnimation by animateFloatAsState(
        targetValue = indicatorProgress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
    )

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 5.dp),
                imageVector = verifyIcon(indicatorProgress),
                contentDescription = stringResource(R.string.DESCRIPTION_ICON_CLOSE),
                tint = Primary,
            )
            Text(
                color = Primary,
                text = verifyText(indicatorProgress),
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .height(30.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth(.9f)
                .clip(RoundedCornerShape(20.dp)),
            progress = progressAnimation,
            color = Primary,
            backgroundColor = BackGroundDark,
        )
        LaunchedEffect(indicatorProgress) {
            progress = indicatorProgress
        }
    }
}

fun verifyIcon(indicatorProgress: Float): ImageVector {
    return if (indicatorProgress < .5) Icons.Rounded.SentimentDissatisfied
    else if (indicatorProgress < .7) Icons.Rounded.SentimentNeutral
    else Icons.Rounded.SentimentVerySatisfied
}

fun verifyText(indicatorProgress: Float): String {
    return if (indicatorProgress == 0f)  ContextProvider.getContext().getString(R.string.LBL_PASS_EMPTY)
    else if (indicatorProgress < .5)  ContextProvider.getContext().getString(R.string.LBL_PASS_WEAK)
    else if (indicatorProgress < .7)  ContextProvider.getContext().getString(R.string.LBL_PASS_MEDIUM)
    else  ContextProvider.getContext().getString(R.string.LBL_PASS_STRONG)
}