package com.janpschwietzer.calpal.presentation.components.dropdown

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.DropdownButton
import com.janpschwietzer.calpal.util.enums.Gender

@Composable
fun GenderDropdown(
    context: Context,
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit
) {
    DropdownButton(
        title = stringResource(R.string.gender),
        options = Gender.entries.map { it.getDisplayedString(context) },
        selectedOption = selectedGender?.getDisplayedString(context) ?: "",
        onOptionSelected = { selectedString ->
            Gender.entries.find { it.getDisplayedString(context) == selectedString }
                ?.let(onGenderSelected)
        }
    )
}
