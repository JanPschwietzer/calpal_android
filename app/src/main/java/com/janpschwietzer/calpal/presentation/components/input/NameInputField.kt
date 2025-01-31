package com.janpschwietzer.calpal.presentation.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R

@Composable
fun NameInputField(name: String?, onNameChange: (String) -> Unit) {
    TextField(
        value = name ?: "",
        onValueChange = onNameChange,
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier.fillMaxWidth(),
    )
}
