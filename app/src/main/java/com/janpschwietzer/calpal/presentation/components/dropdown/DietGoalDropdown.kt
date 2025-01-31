package com.janpschwietzer.calpal.presentation.components.dropdown

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.DropdownButton
import com.janpschwietzer.calpal.util.enums.DietGoal

@Composable
fun DietGoalDropdown(
    context: Context,
    selectedGoal: DietGoal?,
    onDietGoalSelected: (DietGoal) -> Unit
) {

    DropdownButton(
        title = stringResource(R.string.dietgoal),
        options = DietGoal.entries.map { it.getDisplayedString(context) },
        selectedOption = selectedGoal?.getDisplayedString(context) ?: "",
        onOptionSelected = { selectedString ->
            DietGoal.entries.find { it.getDisplayedString(context) == selectedString }
                ?.let(onDietGoalSelected)
        }
    )
}
