package com.janpschwietzer.calpal.presentation.components.dropdown

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.janpschwietzer.calpal.presentation.components.button.DropdownButton
import com.janpschwietzer.calpal.util.enums.PortionUnit

@Composable
fun PortionUnitDropdown(
    context: Context,
    modifier: Modifier = Modifier,
    selected: PortionUnit = PortionUnit.METRICAL,
    onSelected: (PortionUnit) -> Unit
) {
    DropdownButton(
        title = "",
        modifier = modifier,
        options = PortionUnit.entries.map { it.getDisplayedString(context) },
        selectedOption = selected.getDisplayedString(context),
        onOptionSelected = { selectedString ->
            PortionUnit.entries.find { it.getDisplayedString(context) == selectedString }
                ?.let(onSelected)
        }
    )
}