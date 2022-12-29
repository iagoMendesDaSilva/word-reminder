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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.White

@Composable
fun SearchInput(search: MutableState<String>, filter: (text: String) -> Unit) {
    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
        ),
        singleLine = true,
        label = {
            Text(
                stringResource(id = R.string.search),
                color = White,
                style = MaterialTheme.typography.body1
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