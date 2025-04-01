package com.janpschwietzer.calpal.presentation.views.product.edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ProductModel

@Composable
fun ProductInfoTable(product: ProductModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!product.brand.isNullOrEmpty()) {
            InfoRow(
                label = stringResource(R.string.brand),
                value = product.brand
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }

        Text(
            text = stringResource(R.string.nutrition_info),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        product.kcal?.let {
            InfoRow(
                label = stringResource(R.string.calories),
                value = "$it kcal"
            )
        }

        product.carbs?.let {
            InfoRow(
                label = stringResource(R.string.carbs),
                value = "$it g"
            )
        }

        product.sugar?.let {
            InfoRow(
                label = stringResource(R.string.sugar),
                value = "$it g",
                indent = true
            )
        }

        product.fat?.let {
            InfoRow(
                label = stringResource(R.string.fat),
                value = "$it g"
            )
        }

        product.saturatedFat?.let {
            InfoRow(
                label = stringResource(R.string.saturated_fat),
                value = "$it g",
                indent = true
            )
        }

        product.protein?.let {
            InfoRow(
                label = stringResource(R.string.protein),
                value = "$it g"
            )
        }

        product.salt?.let {
            InfoRow(
                label = stringResource(R.string.salt),
                value = "$it g"
            )
        }

        product.fiber?.let {
            InfoRow(
                label = stringResource(R.string.fiber),
                value = "$it g"
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = stringResource(R.string.portion_info),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        InfoRow(
            label = stringResource(R.string.portion_size),
            value = if (product.portionSize != null) "${product.portionSize} ${product.portionUnit ?: "g"}" else "-"
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = stringResource(R.string.scores),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        InfoRow(
            label = "Nutriscore",
            value = product.nutriScore?.name ?: "-"
        )

        InfoRow(
            label = "Greenscore",
            value = product.greenScore?.name ?: "-"
        )
    }
}