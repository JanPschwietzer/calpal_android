package com.janpschwietzer.calpal.presentation.components.dropdown

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.DropdownButton
import com.janpschwietzer.calpal.util.enums.MealTime

@Composable
fun MealTimeDropdown(
    context: Context,
    modifier: Modifier = Modifier,
    selected: MealTime?,
    onSelected: (MealTime) -> Unit
) {
    DropdownButton(
        title = stringResource(R.string.gender),
        modifier = modifier,
        options = MealTime.entries.map { it.getDisplayedString(context) },
        selectedOption = selected?.getDisplayedString(context) ?: "",
        onOptionSelected = { selectedString ->
            MealTime.entries.find { it.getDisplayedString(context) == selectedString }
                ?.let(onSelected)
        }
    )
}