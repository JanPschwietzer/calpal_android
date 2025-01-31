package com.janpschwietzer.calpal.presentation.components.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.dialog.BirthdatePickerDialog
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerButton(
    alternativeTitle: String = stringResource(R.string.choose_date),
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.extraSmall
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ?: alternativeTitle,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = stringResource(R.string.edit_date),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }

    BirthdatePickerDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        selectedDate = selectedDate,
        onDateSelected = { newDate ->
            onDateSelected(newDate)
        }
    )
}

@PreviewLightDark
@Composable
private fun BirthdatePickerViewPreview() {
    CalPalTheme {
        DatePickerButton(
            selectedDate = LocalDate.now(),
            onDateSelected = {}
        )
    }
}