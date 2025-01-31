package com.janpschwietzer.calpal.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.janpschwietzer.calpal.data.source.local.AppDatabase
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.ProductEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        productDao = database.productDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertProductAndGetById() = runTest {
        // Arrange: Ein Testprodukt erstellen
        val product = testProduct

        productDao.insertProduct(product)

        // Act: Produkt aus der DB holen
        val loadedProduct = productDao.getProduct(barcode = 1337)

        // Assert: Prüfen, ob das geladene Produkt korrekt ist
        assertThat(loadedProduct).isNotNull()
        assertThat(loadedProduct?.barcode).isEqualTo(1337)
        assertThat(loadedProduct?.name).isEqualTo("Nutella")
    }

    @Test
    fun insertMultipleProductsAndGetAll() = runTest {
        // Arrange: Mehrere Testprodukte erstellen
        val product1 = testProduct
        val product2 = ProductEntity(
            barcode = 1338,
            name = "Müsli",
            brand = "Kellogg's",
            kcal = 400,
            carbs = 60.0,
            sugar = 20.0,
            fat = 10.0,
            saturatedFat = 5.0,
            protein = 10.0,
            salt = 0.5,
            fiber = 5.0,
            portionSize = 50,
            portionUnit = "g",
            nutriScore = "C",
            greenScore = "B",
            timesAdded = 0,
            isFavorite = false
        )

        productDao.insertProduct(product1)
        productDao.insertProduct(product2)

        // Act: Alle Produkte aus der DB holen
        val allProducts = productDao.getAllProducts()

        // Assert: Prüfen, ob die Anzahl der geladenen Produkte korrekt ist
        assertThat(allProducts).hasSize(2)
    }

    @Test
    fun favoriteProduct() = runTest {
        // Arrange: Ein Testprodukt erstellen
        val product = testProduct

        productDao.insertProduct(product)

        // Act: Produkt als Favorit markieren
        productDao.updateFavorite(1337, true)

        // Assert: Prüfen, ob das Produkt als Favorit markiert ist
        val loadedProduct = productDao.getProduct(barcode = 1337)
        assertThat(loadedProduct?.isFavorite).isTrue()
    }

    @Test
    fun unfavoriteProduct() = runTest {
        // Arrange: Ein Testprodukt erstellen
        val product = ProductEntity(
            barcode = 1337,
            name = "Nutella",
            brand = "Ferrero",
            kcal = 500,
            carbs = 50.0,
            sugar = 30.0,
            fat = 20.0,
            saturatedFat = 10.0,
            protein = 5.0,
            salt = 1.0,
            fiber = 2.0,
            portionSize = 15,
            portionUnit = "g",
            nutriScore = "E",
            greenScore = "D",
            timesAdded = 0,
            isFavorite = true
        )

        productDao.insertProduct(product)

        // Act: Produkt als Favorit markieren und wieder entfernen
        productDao.updateFavorite(1337, false)

        // Assert: Prüfen, ob das Produkt nicht als Favorit markiert ist
        val loadedProduct = productDao.getProduct(barcode = 1337)
        assertThat(loadedProduct?.isFavorite).isFalse()
    }

    @Test
    fun incrementTimesAdded() = runTest {
        // Arrange: Ein Testprodukt erstellen
        val product = testProduct
        productDao.insertProduct(product)

        // Act: Produkt hinzufügen
        productDao.incrementTimesAdded(1337)

        // Assert: Prüfen, ob die Anzahl der hinzugefügten Produkte korrekt ist
        val loadedProduct = productDao.getProduct(barcode = 1337)
        assertThat(loadedProduct?.timesAdded).isEqualTo(1)
    }

    @Test
    fun deleteProduct() = runTest {
        // Arrange: Ein Testprodukt erstellen
        val product = testProduct
        productDao.insertProduct(product)

        // Act: Produkt aus der DB löschen
        productDao.deleteProduct(1337)

        // Assert: Prüfen, ob das Produkt nicht mehr in der DB ist
        val loadedProduct = productDao.getProduct(barcode = 1337)
        assertThat(loadedProduct).isNull()
        assertThat(productDao.getAllProducts()).isEmpty()
    }
}

private val testProduct = ProductEntity(
    barcode = 1337,
    name = "Nutella",
    brand = "Ferrero",
    kcal = 500,
    carbs = 50.0,
    sugar = 30.0,
    fat = 20.0,
    saturatedFat = 10.0,
    protein = 5.0,
    salt = 1.0,
    fiber = 2.0,
    portionSize = 15,
    portionUnit = "g",
    nutriScore = "E",
    greenScore = "D",
    timesAdded = 0,
    isFavorite = false
)
