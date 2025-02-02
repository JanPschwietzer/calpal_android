package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.EatenProductEntity
import com.janpschwietzer.calpal.util.enums.MealTime

data class EatenProductModel(
    val barcode: Long,
    val date: Long,
    val meal: MealTime,
    val amount: Int,
    val unit: String
)

fun EatenProductModel.toEatenProductEntity(): EatenProductEntity {
    return EatenProductEntity(
        barcode = barcode,
        dateEaten = date,
        meal = meal.ordinal,
        amount = amount,
        unit = unit
    )
}