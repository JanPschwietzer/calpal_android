package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.EatenProductEntity
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit

data class EatenProductModel(
    val id: Int?,
    val barcode: Long,
    val date: Long,
    val meal: MealTime,
    val amount: Double,
    val unit: PortionUnit
)

fun EatenProductModel.  toEatenProductEntity(): EatenProductEntity {
    if (id != null) {
        return EatenProductEntity(
            id = id,
            barcode = barcode,
            dateEaten = date,
            meal = meal.ordinal,
            amount = amount,
            unit = unit.ordinal
        )
    }
    return EatenProductEntity(
        barcode = barcode,
        dateEaten = date,
        meal = meal.ordinal,
        amount = amount,
        unit = unit.ordinal
    )
}