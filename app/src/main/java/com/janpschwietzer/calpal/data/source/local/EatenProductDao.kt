package com.janpschwietzer.calpal.data.source.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit

@Entity(tableName = "eaten_product")
data class EatenProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val barcode: Long,
    val dateEaten: Long,
    val meal: Int,
    val amount: Int,
    val unit: Int,
)

fun EatenProductEntity.toEatenProductModel(): EatenProductModel {
    return EatenProductModel(
        barcode = barcode,
        date = dateEaten,
        meal = MealTime.fromId(meal),
        amount = amount,
        unit = PortionUnit.fromId(unit)
    )
}

@Dao
interface EatenProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEatenProduct(eatenProduct: EatenProductEntity): Long

    @Query("SELECT * FROM eaten_product WHERE dateEaten = :date")
    suspend fun getEatenProducts(date: Long): List<EatenProductEntity>
}
