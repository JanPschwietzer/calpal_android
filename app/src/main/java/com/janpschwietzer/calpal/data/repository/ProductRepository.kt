package com.janpschwietzer.calpal.data.repository

import androidx.room.Query
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.model.toProductEntity
import com.janpschwietzer.calpal.data.model.toProductModel
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.toProductModel
import com.janpschwietzer.calpal.data.source.remote.ProductApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ProductRepository {
    suspend fun saveProduct(product: ProductModel)
    suspend fun getProduct(barcode: Long): ProductModel?
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getFavoriteProducts(): List<ProductModel>
    suspend fun searchProducts(query: String): List<ProductModel>
    suspend fun getMostAddedProducts(): List<ProductModel>
    suspend fun updateFavorite(barcode: Long, isFavorite: Boolean)
    suspend fun incrementTimesAdded(barcode: Long)
    suspend fun deleteProduct(barcode: Long)
}

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val productApiService: ProductApiService
): ProductRepository {
    override suspend fun saveProduct(product: ProductModel) {
        productDao.insertProduct(product.toProductEntity())
    }

    override suspend fun getProduct(barcode: Long): ProductModel? {
        return withContext(Dispatchers.IO) {
            val localProduct = productDao.getProduct(barcode)

            if (localProduct != null) {
                return@withContext localProduct.toProductModel()
            }

            val response = productApiService.getProduct(barcode)?.toProductModel()

            if (response != null) {
                productDao.insertProduct(response.toProductEntity())

                return@withContext response
            }
                return@withContext null
        }
    }

    override suspend fun getAllProducts(): List<ProductModel> {
        return productDao.getAllProducts().map { it.toProductModel() }
    }

    override suspend fun getFavoriteProducts(): List<ProductModel> {
        return productDao.getFavoriteProducts().map { it.toProductModel() }
    }


    override suspend fun searchProducts(query: String): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            return@withContext productDao.searchProducts(query).map { it.toProductModel() }
        }
    }

    override suspend fun getMostAddedProducts(): List<ProductModel> {
        return productDao.getMostAddedProducts().map { it.toProductModel() }
    }

    override suspend fun updateFavorite(barcode: Long, isFavorite: Boolean) {
        productDao.updateFavorite(barcode, isFavorite)
    }

    override suspend fun incrementTimesAdded(barcode: Long) {
        productDao.incrementTimesAdded(barcode)
    }

    override suspend fun deleteProduct(barcode: Long) {
        productDao.deleteProduct(barcode)
    }
}