package com.janpschwietzer.calpal.presentation.components.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R

@Composable
fun WeightHeightInput(
    weight: Int?,
    height: Int?,
    onWeightChange: (Int?) -> Unit,
    onHeightChange: (Int?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = weight?.toString() ?: "",
            onValueChange = { onWeightChange(it.toIntOrNull()) },
            label = { Text(stringResource(R.string.weight_kg)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = height?.toString() ?: "",
            onValueChange = { onHeightChange(it.toIntOrNull()) },
            label = { Text(stringResource(R.string.height_cm)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
            )
        )
    }
}
