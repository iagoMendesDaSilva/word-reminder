package com.iago.reminder.screens.password.commons

import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    LaunchedEffect(indicatorProgress) {
        progress = indicatorProgress
    }

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
                contentDescription = null,
                tint = Primary,
            )
            Text(
                color = Primary,
                text = verifyText(indicatorProgress, LocalContext.current),
                style = MaterialTheme.typography.body2
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
    }
}

fun verifyIcon(indicatorProgress: Float): ImageVector {
    return if (indicatorProgress < .5) Icons.Filled.SentimentDissatisfied
    else if (indicatorProgress < .7) Icons.Filled.SentimentNeutral
    else Icons.Filled.SentimentVerySatisfied
}

fun verifyText(indicatorProgress: Float, context: Context): String {
    return if (indicatorProgress == 0f) context.getString(R.string.no_password)
    else if (indicatorProgress < .5) context.getString(R.string.weak)
    else if (indicatorProgress < .7) context.getString(R.string.regular)
    else context.getString(R.string.strong)
}