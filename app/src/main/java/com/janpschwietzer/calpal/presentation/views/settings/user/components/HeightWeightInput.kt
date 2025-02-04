package com.janpschwietzer.calpal.presentation.views.settings.user.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField

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
        BasicInputField(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.weight_kg),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = weight?.toString(),
            onValueChange = { onWeightChange(it.toIntOrNull()) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicInputField(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.height_cm),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = height?.toString(),
            onValueChange = { onHeightChange(it.toIntOrNull()) }
        )
    }
}
