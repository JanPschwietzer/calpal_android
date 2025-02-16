package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.ProductEntity
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

data class ProductModel(
    val barcode: Long,
    val name: String? = null,
    val brand: String? = null,
    val kcal: Int? = null,
    val carbs: Double? = null,
    val sugar: Double? = null,
    val fat: Double? = null,
    val saturatedFat: Double? = null,
    val protein: Double? = null,
    val salt: Double? = null,
    val fiber: Double? = null,
    val portionSize: Int? = null,
    val portionUnit: String? = null,
    val nutriScore: NutriScore? = null,
    val greenScore: GreenScore? = null,
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