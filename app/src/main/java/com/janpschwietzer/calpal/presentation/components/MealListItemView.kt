package com.janpschwietzer.calpal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.net.URLEncoder

@Composable
fun MealListItemView(
    navController: NavHostController,
    meal: String,
    caloriesEaten: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .clickable {
                navController.navigate("details/${URLEncoder.encode(meal, "UTF-8")}")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Food",
                modifier = Modifier.size(24.dp)
            )

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    meal,
                    fontWeight = FontWeight.SemiBold
                )
                Text("${caloriesEaten} / ${caloriesGoal} kcal")
            }
        }
            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    navController.navigate("add-product/${URLEncoder.encode(meal, "UTF-8")}")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_product),
                )
            }
        }
}

@PreviewLightDark
@Composable
private fun MealListItemViewPreview() {
    CalPalTheme {
        MealListItemView(
            navController = rememberNavController(),
            meal = "Breakfast",
            caloriesEaten = 0,
            caloriesGoal = 630
        )
    }
}