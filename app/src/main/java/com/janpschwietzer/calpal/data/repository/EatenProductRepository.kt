package com.janpschwietzer.calpal.data.repository

import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.toEatenProductEntity
import com.janpschwietzer.calpal.data.source.local.EatenProductDao
import com.janpschwietzer.calpal.data.source.local.toEatenProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

interface EatenProductRepository {
    suspend fun saveEatenProduct(eatenProduct: EatenProductModel)
    suspend fun getEatenProducts(date: LocalDate): List<EatenProductModel>
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
}