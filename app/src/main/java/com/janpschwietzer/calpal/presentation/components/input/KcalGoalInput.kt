package com.janpschwietzer.calpal.presentation.components.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R

@Composable
fun KcalGoalInput(
    kcalGoal: Int?,
    isEditable: Boolean,
    onKcalGoalChange: (Int?) -> Unit,
    onToggleEditable: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = kcalGoal?.toString() ?: "",
            onValueChange = { onKcalGoalChange(it.toIntOrNull()) },
            label = { Text(stringResource(R.string.daily_kcalgoal)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = isEditable,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .clickable { onToggleEditable(!isEditable) }
        ) {
            Checkbox(
                checked = isEditable,
                onCheckedChange = onToggleEditable
            )
            Text(stringResource(R.string.edit_kcal_goal))
        }
    }
}
