package com.iago.reminder.helpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.iago.reminder.R

@Composable
fun Logo() {
    Image(
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth(.7f),
        painter = rememberAsyncImagePainter(R.drawable.logo),
        contentDescription = stringResource(R.string.logo_desc),
    )
}