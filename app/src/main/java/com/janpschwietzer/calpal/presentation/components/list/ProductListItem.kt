package com.janpschwietzer.calpal.presentation.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.janpschwietzer.calpal.data.model.ProductModel

@Composable
fun ProductListItem(
    product: ProductModel,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(8.dp)
    ) {
        //TODO: Add image of product
        Column {
            Text(
                text = product.name ?: "",
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = product.brand ?: "",
                maxLines = 1
            )
        }
    }
}