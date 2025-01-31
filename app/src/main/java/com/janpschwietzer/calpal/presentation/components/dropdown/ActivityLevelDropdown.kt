package com.janpschwietzer.calpal.presentation.components.dropdown

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.DropdownButton
import com.janpschwietzer.calpal.util.enums.ActivityLevel

@Composable
fun ActivityLevelDropdown(
    context: Context,
    selectedLevel: ActivityLevel?,
    onActivityLevelSelected: (ActivityLevel) -> Unit
) {

    DropdownButton(
        title = stringResource(R.string.activitylevel),
        options = ActivityLevel.entries.map { it.getDisplayedString(context) },
        selectedOption = selectedLevel?.getDisplayedString(context) ?: "",
        onOptionSelected = { selectedString ->
            ActivityLevel.entries.find { it.getDisplayedString(context) == selectedString }
                ?.let(onActivityLevelSelected)
        }
    )
}
