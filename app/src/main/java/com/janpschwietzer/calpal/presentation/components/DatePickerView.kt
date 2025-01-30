package com.janpschwietzer.calpal.presentation.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun DatePickerView(
    alternativeTitle: String = stringResource(R.string.choose_date),
    context: Context = LocalContext.current,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val selectedDate = LocalDate.of(year, month + 1, day)
            onDateSelected(selectedDate)
        },
        date?.year ?: calendar.get(Calendar.YEAR),
        date?.monthValue?.minus(1) ?: calendar.get(Calendar.MONTH),
        date?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }
            .background(TextFieldDefaults.colors().unfocusedContainerColor)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (date != null) {
                date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            } else {
                alternativeTitle
            },
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = stringResource(R.string.edit_date),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@PreviewLightDark
@Composable
private fun BirthdatePickerViewPreview() {
    CalPalTheme {
        DatePickerView(
            date = null,
            onDateSelected = {}
        )
    }
}