package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun SearchInput(search: MutableState<String>, filter: (text: String) -> Unit) {
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            backgroundColor = MaterialTheme.colors.onBackground,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

            ),
        singleLine = true,
        label = {
            Text(
                color = WhiteOpacity,
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.search),
            )
        },
        leadingIcon = {
            Icon(
                tint = White,
                contentDescription = null,
                imageVector = Icons.Default.Search,
            )
        },
        trailingIcon = {
            if (search.value.isNotEmpty())
                Icon(
                    tint = White,
                    contentDescription = null,
                    imageVector = Icons.Default.Close,
                    modifier = Modifier.clickable {
                        search.value = ""
                    }
                )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = MaterialTheme.shapes.medium,
        value = search.value, onValueChange = {
            search.value = it
            filter(it)
        })
}