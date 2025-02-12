package com.janpschwietzer.calpal.presentation.components.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import com.janpschwietzer.calpal.util.helpers.CalculationHelper
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EatenProductListItem(
    eatenProduct: EatenProductModel,
    product: ProductModel?,
    onClick: () -> Unit = {},
    onDelete: () -> Unit // Callback-Funktion für das Löschen des Elements
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete() // Löschen des Elements
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.LightGray,
                label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Löschen",
                    tint = Color.White
                )
            }
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = product?.name ?: "",
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = product?.brand ?: "",
                            maxLines = 1
                        )
                    }
                    Text("${CalculationHelper.calculateCalories(eatenProduct, product)} kcal")
                }
            }
        }
    )
}


@PreviewLightDark
@Composable
fun PreviewEatenProductListItem() {
    CalPalTheme {
        EatenProductListItem(
            eatenProduct = EatenProductModel(
                id = null,
                barcode = 1337,
                meal = MealTime.SNACK,
                amount = 13,
                date = LocalDateConverter.toTimestamp(LocalDate.now())!!,
                unit = PortionUnit.METRICAL
            ),
            product = ProductModel(
                1337,
                kcal = 530,
                name = "Nutella",
                brand = "Ferrero",
                carbs = null,
                sugar = null,
                fat = null,
                saturatedFat = null,
                protein = null,
                salt = null,
                fiber = null,
                portionSize = null,
                portionUnit = null,
                nutriScore = null,
                greenScore = null,
                timesAdded = 0,
                isFavorite = false,
            ),
            onClick = { },
            onDelete = { }
        )
    }
}