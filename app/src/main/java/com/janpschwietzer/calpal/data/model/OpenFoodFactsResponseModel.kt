package com.janpschwietzer.calpal.data.model

import com.google.gson.annotations.SerializedName
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

data class OpenFoodFactsResponseModel(
    @SerializedName("code") val barcode: String,
    @SerializedName("product") val product: ProductData?,
    @SerializedName("status") val status: Int
)

data class ProductData(
    @SerializedName("product_name") val name: String?,
    @SerializedName("brands") val brand: String?,
    @SerializedName("nutriments") val nutriments: Nutriments?,
    @SerializedName("nutriscore_grade") val nutriScore: String?,
    @SerializedName("ecoscore_grade") val greenScore: String?,
    @SerializedName("serving_quantity") val portionSize: String?,
    @SerializedName("serving_quantity_unit") val portionUnit: String?
)

data class Nutriments(
    @SerializedName("energy-kcal_100g") val kcal: Double?,
    @SerializedName("carbohydrates_100g") val carbs: Double?,
    @SerializedName("sugars_100g") val sugar: Double?,
    @SerializedName("fat_100g") val fat: Double?,
    @SerializedName("saturated-fat_100g") val saturatedFat: Double?,
    @SerializedName("proteins_100g") val protein: Double?,
    @SerializedName("salt_100g") val salt: Double?,
    @SerializedName("fiber_100g") val fiber: Double?
)

fun OpenFoodFactsResponseModel.toProductModel() : ProductModel? {
    if (status != 1) {
        return null
    }

    return ProductModel(
        barcode = barcode.toLong(),
        name = product?.name,
        brand = product?.brand,
        kcal = product?.nutriments?.kcal?.toInt(),
        carbs = product?.nutriments?.carbs,
        sugar = product?.nutriments?.sugar,
        fat = product?.nutriments?.fat,
        saturatedFat = product?.nutriments?.saturatedFat,
        protein = product?.nutriments?.protein,
        salt = product?.nutriments?.salt,
        fiber = product?.nutriments?.fiber,
        portionSize = product?.portionSize?.toIntOrNull(),
        portionUnit = product?.portionUnit,
        nutriScore = product?.nutriScore?.let { NutriScore.fromRating(it) },
        greenScore = product?.greenScore?.let { GreenScore.fromRating(it) },
        timesAdded = 0,
        isFavorite = false
    )
}
