package com.janpschwietzer.calpal.presentation.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R

@Composable
fun NameInputField(name: String?, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name ?: "",
        onValueChange = onNameChange,
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
        )
    )
}
