package com.janpschwietzer.calpal.data.source.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.janpschwietzer.calpal.data.model.UserModel
import com.janpschwietzer.calpal.util.enums.ActivityLevel
import com.janpschwietzer.calpal.util.enums.DietGoal
import com.janpschwietzer.calpal.util.enums.Gender
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val name: String?,
    var birthdate: Long?,
    var height: Int?,
    var weight: Int?,
    var gender: Int?,
    var activityLevel: Int?,
    var dietGoal: Int?,
    var kcalGoal: Int?
)


fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        name = name,
        birthdate = LocalDateConverter.toLocalDate(birthdate) ?: LocalDate.now(),
        height = height,
        weight = weight,
        gender = gender?.let { Gender.fromId(it) },
        activityLevel = activityLevel?.let { ActivityLevel.fromId(it) },
        dietGoal = dietGoal?.let { DietGoal.fromId(it) },
        kcalGoal = kcalGoal
    )
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?
}