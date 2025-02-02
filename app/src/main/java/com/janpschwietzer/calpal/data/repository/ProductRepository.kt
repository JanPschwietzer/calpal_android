package com.janpschwietzer.calpal.data.repository

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
    suspend fun toggleFavorite(product: ProductModel)
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
        return withContext(Dispatchers.IO) {
            return@withContext productDao.getAllProducts().map { it.toProductModel() }
        }
    }

    override suspend fun getFavoriteProducts(): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            return@withContext productDao.getFavoriteProducts().map { it.toProductModel() }
        }
    }


    override suspend fun searchProducts(query: String): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            return@withContext productDao.searchProducts(query).map { it.toProductModel() }
        }
    }

    override suspend fun getMostAddedProducts(): List<ProductModel> {
        return withContext(Dispatchers.IO) {
            return@withContext productDao.getMostAddedProducts().map { it.toProductModel() }
        }
    }

    override suspend fun toggleFavorite(product: ProductModel) {
        withContext(Dispatchers.IO) {
            productDao.updateFavorite(product.barcode, !product.isFavorite)
        }
    }

    override suspend fun incrementTimesAdded(barcode: Long) {
        withContext(Dispatchers.IO) {
            productDao.incrementTimesAdded(barcode)
        }
    }

    override suspend fun deleteProduct(barcode: Long) {
        withContext(Dispatchers.IO) {
            productDao.deleteProduct(barcode)
        }
    }
}