package com.janpschwietzer.calpal.data.source.local

import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.util.enums.GreenScore
import com.janpschwietzer.calpal.util.enums.NutriScore

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val barcode: Long,
    val name: String?,
    val brand: String?,
    val kcal: Int?,
    val carbs: Double?,
    val sugar: Double?,
    val fat: Double?,
    val saturatedFat: Double?,
    val protein: Double?,
    val salt: Double?,
    val fiber: Double?,
    val portionSize: Int?,
    val portionUnit: String?,
    val nutriScore: String?,
    val greenScore: String?,
    val timesAdded: Int = 0,
    val isFavorite: Boolean = false
)

fun ProductEntity.toProductModel(): ProductModel {
    return ProductModel(
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
        nutriScore = nutriScore?.let { NutriScore.fromRating(it) },
        greenScore = greenScore?.let { GreenScore.fromRating(it) },
        timesAdded = timesAdded,
        isFavorite = isFavorite
    )
}

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity): Long

    @Query("SELECT * FROM product WHERE barcode = :barcode")
    suspend fun getProduct(barcode: Long): ProductEntity?

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE isFavorite = 1")
    suspend fun getFavoriteProducts(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%' OR brand LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductEntity>

    @Query("SELECT * FROM product ORDER BY timesAdded DESC")
    suspend fun getMostAddedProducts(): List<ProductEntity>

    @Query("UPDATE product SET isFavorite = :isFavorite WHERE barcode = :barcode")
    suspend fun updateFavorite(barcode: Long, isFavorite: Boolean)

    @Query("UPDATE product SET timesAdded = timesAdded + 1 WHERE barcode = :barcode")
    suspend fun incrementTimesAdded(barcode: Long)

    @Query("DELETE FROM product WHERE barcode = :barcode")
    suspend fun deleteProduct(barcode: Long)
}