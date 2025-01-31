package com.janpschwietzer.calpal.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.janpschwietzer.calpal.data.repository.ProductRepositoryImpl
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.ProductDatabase
import com.janpschwietzer.calpal.data.source.local.ProductEntity
import com.janpschwietzer.calpal.data.source.remote.ProductApiService
import com.janpschwietzer.calpal.data.source.remote.RetrofitClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryTest {

    private lateinit var database: ProductDatabase
    private lateinit var productDao: ProductDao
    private lateinit var apiService: ProductApiService
    private lateinit var productRepository: ProductRepositoryImpl

    @Before
    fun setup() {
        // Create in-memory database
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        productDao = database.productDao()

        // Use real API service
        apiService = RetrofitClient.apiService

        // Initialize repository with real database & real API
        productRepository = ProductRepositoryImpl(productDao, apiService)
    }

    @After
    fun teardown() {
        database.close() // Close DB after tests
    }

    @Test
    fun getLocalProduct() = runTest {
        // Arrange: Lokales Produkt simulieren
        val barcode = 737628064502
        val localProduct = ProductEntity(
            barcode = barcode,
            name = "Test-Produkt",
            brand = "Test-Marke",
            kcal = 100,
            carbs = 20.0,
            sugar = 5.0,
            fat = 10.0,
            saturatedFat = 3.0,
            protein = 7.0,
            salt = 1.0,
            fiber = 2.0,
            portionSize = 100,
            portionUnit = "g",
            nutriScore = "B",
            greenScore = "A"
        )

        // DAO gibt ein lokales Produkt zurück
        productDao.insertProduct(localProduct)

        // Act: Repository aufrufen
        val result = productRepository.getProduct(barcode)

        // Assert: Erwartetes Produkt zurückgegeben?
        assertNotNull(result)
        assertEquals("Test-Produkt", result?.name)
    }

    @Test
    fun getAPIProduct() = runTest {
        // Arrange: Use a known barcode from OpenFoodFacts
        val barcode = 5000112552232 // Example: Coca-Cola Zero Sugar

        // Act: Call the repository method
        val product = productRepository.getProduct(barcode)

        // Assert: Product should be fetched from API
        assertNotNull(product)
        assertEquals("Coca Cola Zero Sugar", product?.name)

        // Verify: Product is stored in the local database
        val dbProduct = productDao.getProduct(barcode)
        assertNotNull(dbProduct)
        assertEquals("Coca Cola Zero Sugar", dbProduct?.name)
    }

    @Test
    fun getNoProduct() = runTest {
        // Arrange
        val barcode = 133713371337L

        // Act
        val product = productRepository.getProduct(barcode)

        // Assert
        assertNull(product)
    }

    @Test
    fun getSearchedProducts() = runTest {
        // Arrange
        val query = "Test-Produkt"
        val localProduct = ProductEntity(
            barcode = 737628064502,
            name = "Test-Produkt",
            brand = "Test-Marke",
            kcal = 100,
            carbs = 20.0,
            sugar = 5.0,
            fat = 10.0,
            saturatedFat = 3.0,
            protein = 7.0,
            salt = 1.0,
            fiber = 2.0,
            portionSize = 100,
            portionUnit = "g",
            nutriScore = "B",
            greenScore = "A"
        )

        productDao.insertProduct(localProduct)

        // Act
        val products = productRepository.searchProducts(query)

        // Assert
        assertTrue(products.isNotEmpty())
    }
}
