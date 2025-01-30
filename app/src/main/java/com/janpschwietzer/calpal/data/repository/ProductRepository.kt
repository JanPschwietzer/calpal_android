package com.janpschwietzer.calpal.data.repository

import androidx.room.Query
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.model.toProductEntity
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.toProductModel

interface ProductRepository {
    suspend fun saveProduct(product: ProductModel)
    suspend fun getProduct(barcode: Int): ProductModel?
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getFavoriteProducts(): List<ProductModel>
    suspend fun searchProducts(query: String): List<ProductModel>
    suspend fun getMostAddedProducts(): List<ProductModel>
    suspend fun updateFavorite(barcode: Int, isFavorite: Boolean)
    suspend fun incrementTimesAdded(barcode: Int)
    suspend fun deleteProduct(barcode: Int)
}

class ProductRepositoryImpl(private val productDao: ProductDao): ProductRepository {
    override suspend fun saveProduct(product: ProductModel) {
        productDao.insertProduct(product.toProductEntity())
    }

    override suspend fun getProduct(barcode: Int): ProductModel? {
        return productDao.getProduct(barcode)?.toProductModel()
    }

    override suspend fun getAllProducts(): List<ProductModel> {
        return productDao.getAllProducts().map { it.toProductModel() }
    }

    override suspend fun getFavoriteProducts(): List<ProductModel> {
        return productDao.getFavoriteProducts().map { it.toProductModel() }
    }

    override suspend fun searchProducts(query: String): List<ProductModel> {
        return productDao.searchProducts(query).map { it.toProductModel() }
    }

    override suspend fun getMostAddedProducts(): List<ProductModel> {
        return productDao.getMostAddedProducts().map { it.toProductModel() }
    }

    override suspend fun updateFavorite(barcode: Int, isFavorite: Boolean) {
        productDao.updateFavorite(barcode, isFavorite)
    }

    override suspend fun incrementTimesAdded(barcode: Int) {
        productDao.incrementTimesAdded(barcode)
    }

    override suspend fun deleteProduct(barcode: Int) {
        productDao.deleteProduct(barcode)
    }
}