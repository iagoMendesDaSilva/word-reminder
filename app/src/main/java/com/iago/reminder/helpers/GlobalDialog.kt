package com.iago.reminder.helpers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.iago.reminder.R
import com.iago.reminder.ui.theme.*

@Composable
fun GlobalDialog(
    messageID: Int,
    error: Boolean = true,
    imageIconTwoOptions: Int? = null,
    onDismiss: () -> Unit,
    onSuccess: (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .height(250.dp)
                    .background(color = White, shape = MaterialTheme.shapes.medium)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    painter = painterResource(
                        imageIconTwoOptions
                            ?: if (error) R.drawable.icon_error else R.drawable.icon_check
                    ),
                    modifier = Modifier
                        .width(if (imageIconTwoOptions != null) 70.dp else 60.dp)
                        .height(if (imageIconTwoOptions != null) 70.dp else 60.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageIconTwoOptions === null)
                        Text(
                            color = if (error) Error else Success,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2,
                            text = if (error) stringResource(R.string.oops) else stringResource(R.string.success),
                        )
                    Text(
                        textAlign = TextAlign.Center,
                        style = if (imageIconTwoOptions != null)
                            MaterialTheme.typography.body2
                        else MaterialTheme.typography.body1,
                        text = stringResource(id = messageID),
                        modifier = Modifier.padding(vertical = 3.dp),
                        color = if (imageIconTwoOptions != null) Black else BlackOpacity,
                    )
                }
                if (imageIconTwoOptions != null)
                    TwoOptionButton(onSuccess!!, onDismiss)
                else
                    OneOptionButton(onDismiss, error)
            }
        }
    )
}

@Composable
fun OneOptionButton(onDismiss: () -> Unit, error: Boolean) {
    Button(
        onClick = { onDismiss() },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(.5f),
        colors = ButtonDefaults.buttonColors(backgroundColor = if (error) Error else Success),
    ) {
        Text(
            color = White,
            text = stringResource(R.string.ok),
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
fun TwoOptionButton(onSuccess: () -> Unit, onDismiss: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { onDismiss() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(.45f),
            border = BorderStroke(2.dp, BlueInfo),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = White)
        ) {
            Text(
                color = BlueInfo,
                style = MaterialTheme.typography.button,
                text = stringResource(R.string.button_cancel),
            )
        }
        Button(
            onClick = { onSuccess() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(.81f),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = BlueInfo)
        ) {
            Text(
                color = White,
                style = MaterialTheme.typography.button,
                text = stringResource(R.string.button_confirm),
            )
        }
    }
}