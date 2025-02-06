package com.janpschwietzer.calpal.data.repository

import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.toEatenProductEntity
import com.janpschwietzer.calpal.data.source.local.EatenProductDao
import com.janpschwietzer.calpal.data.source.local.toEatenProductModel
import com.janpschwietzer.calpal.util.enums.MealTime
import java.time.LocalDate

interface EatenProductRepository {
    suspend fun saveEatenProduct(eatenProduct: EatenProductModel)
    suspend fun getEatenProducts(date: LocalDate): List<EatenProductModel>
    suspend fun getEatenProducts(date: LocalDate, mealTime: MealTime): List<EatenProductModel>
}

class EatenProductRepositoryImpl(
    private val eatenProductDao: EatenProductDao
): EatenProductRepository {
    override suspend fun saveEatenProduct(eatenProduct: EatenProductModel) {
        eatenProductDao.insertEatenProduct(eatenProduct.toEatenProductEntity())
    }

    override suspend fun getEatenProducts(date: LocalDate): List<EatenProductModel> {
        return eatenProductDao.getEatenProducts(date.toEpochDay()).map { it.toEatenProductModel() }
    }

    override suspend fun getEatenProducts(date: LocalDate, mealTime: MealTime): List<EatenProductModel> {
        return eatenProductDao.getEatenProducts(date.toEpochDay(), meal = mealTime.ordinal).map { it.toEatenProductModel() }
    }
}