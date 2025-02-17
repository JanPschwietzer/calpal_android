package com.janpschwietzer.calpal.presentation.views.settings.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.button.DatePickerButton
import com.janpschwietzer.calpal.presentation.components.button.SaveButton
import com.janpschwietzer.calpal.presentation.components.dropdown.ActivityLevelDropdown
import com.janpschwietzer.calpal.presentation.components.dropdown.DietGoalDropdown
import com.janpschwietzer.calpal.presentation.components.dropdown.GenderDropdown
import com.janpschwietzer.calpal.presentation.components.input.KcalGoalInput
import com.janpschwietzer.calpal.presentation.components.input.BasicInputField
import com.janpschwietzer.calpal.presentation.views.settings.user.components.WeightHeightInput
import com.janpschwietzer.calpal.presentation.layout.CustomScaffold
import com.janpschwietzer.calpal.ui.theme.CalPalTheme


@Composable
fun UserSettingsScreen(
    navController: NavHostController,
    viewModel: UserSettingsViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val kcalGoalEditable by viewModel.kcalGoalEditable.collectAsState()
    val scrollState = rememberScrollState()

    CustomScaffold(
        title = stringResource(R.string.user_settings_title),
        showCloseButton = true,
        showFab = false,
        navController = navController,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            BasicInputField(
                label = stringResource(R.string.name),
                value = user?.name,
                onValueChange = viewModel::updateName
            )

            Spacer(modifier = Modifier.height(8.dp))

            DatePickerButton(
                alternativeTitle = stringResource(R.string.choose_date),
                selectedDate = user?.birthdate,
                onDateSelected = viewModel::updateBirthdate
            )

            Spacer(modifier = Modifier.height(8.dp))

            GenderDropdown(
                context = navController.context,
                selectedGender = user?.gender,
                onGenderSelected = viewModel::updateGender
            )

            Spacer(modifier = Modifier.height(8.dp))

            WeightHeightInput(user?.weight, user?.height, viewModel::updateWeight, viewModel::updateHeight)

            Spacer(modifier = Modifier.height(8.dp))

            ActivityLevelDropdown(
                context = navController.context,
                selectedLevel = user?.activityLevel,
                onActivityLevelSelected = viewModel::updateActivityLevel
            )

            Spacer(modifier = Modifier.height(16.dp))

            DietGoalDropdown(
                context = navController.context,
                selectedGoal = user?.dietGoal,
                onDietGoalSelected = viewModel::updateDietGoal
            )

            Spacer(modifier = Modifier.height(16.dp))

            KcalGoalInput(user?.kcalGoal, kcalGoalEditable, viewModel::updateKcalGoal, viewModel::setKcalGoalEditable)

            Spacer(modifier = Modifier.height(16.dp))

            SaveButton { viewModel.saveUserSettings() }
        }
    }
}


@PreviewLightDark
@Composable
fun UserSettingsScreenPreview() {
    CalPalTheme {
        UserSettingsScreen(navController = rememberNavController())
    }
}