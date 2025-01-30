package com.janpschwietzer.calpal.presentation.views.settings.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.presentation.components.DatePickerView
import com.janpschwietzer.calpal.presentation.components.DropdownView
import com.janpschwietzer.calpal.presentation.components.ScaffoldView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.ActivityLevel
import com.janpschwietzer.calpal.util.enums.DietGoal
import com.janpschwietzer.calpal.util.enums.Gender

@Composable
fun UserSettingsScreen(
    navController: NavHostController,
    viewModel: UserSettingsViewModel = hiltViewModel()
    ) {
    val user by viewModel.user.collectAsState()
    val kcalGoalEditable by viewModel.kcalGoalEditable.collectAsState()
    val context = LocalContext.current

    ScaffoldView(
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
        ) {
            TextField(
                value = user.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            DatePickerView(
                alternativeTitle = stringResource(R.string.choose_date),
                context = LocalContext.current,
                date = user.birthdate
            ) {
                viewModel.updateBirthdate(it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            DropdownView(
                title = stringResource(R.string.gender),
                Gender.entries.map { it.getDisplayedString(context) },
                selectedOption = user.gender.getDisplayedString(context),
                onOptionSelected = { selectedString ->
                    val selectedActivityLevel = Gender.entries.find { it.getDisplayedString(context) == selectedString }
                    selectedActivityLevel?.let { viewModel.updateGender(it) }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                TextField(
                    value = user.weight.toString(),
                    onValueChange = { viewModel.updateWeight(it.toIntOrNull() ?: 0) },
                    label = { Text(stringResource(R.string.weight_kg)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = user.height.toString(),
                    onValueChange = { viewModel.updateHeight(it.toIntOrNull() ?: 0) },
                    label = { Text(stringResource(R.string.height_cm)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DropdownView(
                title = stringResource(R.string.activitylevel),
                ActivityLevel.entries.map { it.getDisplayedString(context) },
                selectedOption = user.activityLevel.getDisplayedString(context),
                onOptionSelected = { selectedString ->
                    val selectedActivityLevel = ActivityLevel.entries.find { it.getDisplayedString(context) == selectedString }
                    selectedActivityLevel?.let { viewModel.updateActivityLevel(it) }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))


            DropdownView(
                title = stringResource(R.string.dietgoal),
                options = DietGoal.entries.map { it.getDisplayedString(context) },
                selectedOption = user.dietGoal.getDisplayedString(context),
                onOptionSelected = { selectedString ->
                    val selectedDietGoal = DietGoal.entries.find { it.getDisplayedString(context) == selectedString }
                    selectedDietGoal?.let { viewModel.updateDietGoal(it) }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = user.kcalGoal.toString(),
                    onValueChange = { viewModel.updateKcalGoal(it.toIntOrNull() ?: 0) },
                    label = { Text(stringResource(R.string.daily_kcalgoal)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = kcalGoalEditable,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            viewModel.setKcalGoalEditable(!kcalGoalEditable)
                        },
                ) {
                    Checkbox(
                        checked = kcalGoalEditable,
                        onCheckedChange = { viewModel.setKcalGoalEditable(it) }
                    )
                    Text(
                        stringResource(R.string.edit_kcal_goal)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.saveUserSettings() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Text(stringResource(R.string.save))
            }
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