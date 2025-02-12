package com.janpschwietzer.calpal.util.helpers

import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.util.enums.PortionUnit

object CalculationHelper {
    fun calculateCalories(eatenProduct: EatenProductModel, product: ProductModel?): Int {
        return when (eatenProduct.unit) {
            PortionUnit.METRICAL ->
                (eatenProduct.amount * (product?.kcal ?: 100) / 100).toInt()
            PortionUnit.PORTION ->
                (eatenProduct.amount * (product?.portionSize ?: 1) * (product?.kcal ?: 100) / 100).toInt()
        }
    }
}
