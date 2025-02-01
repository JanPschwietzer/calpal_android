package com.janpschwietzer.calpal.data.source.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.util.enums.MealTime

@Entity(tableName = "eaten_product")
data class EatenProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val barcode: Long,
    val dateEaten: Long,
    val meal: Int,
    val amount: Int,
    val unit: String,
)

fun EatenProductEntity.toEatenProductModel(): EatenProductModel {
    return EatenProductModel(
        id = id,
        barcode = barcode,
        date = dateEaten,
        meal = MealTime.fromId(meal),
        amount = amount,
        unit = unit
    )
}

@Dao
interface EatenProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEatenProduct(eatenProduct: EatenProductEntity): Long

    @Query("SELECT * FROM eaten_product WHERE dateEaten = :date")
    fun getEatenProducts(date: Long): List<EatenProductEntity>
}
