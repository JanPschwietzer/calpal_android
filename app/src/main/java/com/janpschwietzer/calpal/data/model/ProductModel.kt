package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.ProductEntity
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

data class ProductModel(
    val barcode: Long,
    val name: String?,
    val brand: String?,
    val kcal: Int?,
    val carbs: Double?,
    val sugar: Double?,
    val fat: Double?,
    val saturatedFat: Double?,
    val protein: Double?,
    val salt: Double?,
    val fiber: Double?,
    val portionSize: Int?,
    val portionUnit: String?,
    val nutriScore: NutriScore?,
    val greenScore: GreenScore?,
    val timesAdded: Int = 0,
    val isFavorite: Boolean = false
)

fun ProductModel.toProductEntity(): ProductEntity {
    return ProductEntity(
        barcode = barcode,
        name = name,
        brand = brand,
        kcal = kcal,
        carbs = carbs,
        sugar = sugar,
        fat = fat,
        saturatedFat = saturatedFat,
        protein = protein,
        salt = salt,
        fiber = fiber,
        portionSize = portionSize,
        portionUnit = portionUnit,
        nutriScore = nutriScore?.rating,
        greenScore = greenScore?.rating,
        timesAdded = timesAdded,
        isFavorite = isFavorite
    )
}