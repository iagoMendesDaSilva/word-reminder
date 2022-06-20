package com.iago.reminder.helpers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.Gray
import com.iago.reminder.ui.theme.White

@Composable
fun SwitchDefault(initialValue: Boolean = false, onChange: (value: Boolean) -> Unit) {

    var xState by remember { mutableStateOf(0) }
    var active = remember { mutableStateOf(false) }

    val xOffset = animateIntAsState(
        targetValue = xState,
        animationSpec = tween(durationMillis = 150, easing = LinearEasing)
    )

    LaunchedEffect(key1 = initialValue){
        xState = if (initialValue) 35 else 0
        active.value = initialValue
    }

    Row(
        modifier = Modifier
            .width(60.dp)
            .height(30.dp)
            .clickable {
                xState = if (active.value) 0 else 35
                active.value = !active.value
                onChange(active.value)
            }
            .background(
                color = if (active.value) MaterialTheme.colors.primary else Gray,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(25.dp)
                .height(25.dp)
                .offset(x = xOffset.value.dp)
                .padding(3.5.dp)
                .background(color = White, shape = MaterialTheme.shapes.large)

        )
    }
}