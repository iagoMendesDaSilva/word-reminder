package com.iago.reminder.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.iago.reminder.R

@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.logo_desc),
        contentScale = ContentScale.Fit,
        modifier = modifier.fillMaxWidth(0.6f)
    )
}
