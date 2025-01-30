package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.UserEntity
import com.janpschwietzer.calpal.util.enums.ActivityLevel
import com.janpschwietzer.calpal.util.enums.DietGoal
import com.janpschwietzer.calpal.util.enums.Gender
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import java.time.LocalDate

data class UserModel(
    val name: String? = null,
    val birthdate: LocalDate? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val gender: Gender? = null,
    val activityLevel: ActivityLevel? = null,
    val dietGoal: DietGoal? = null,
    val kcalGoal: Int? = null
)

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        name = name,
        birthdate = LocalDateConverter.toTimestamp(birthdate) ?: LocalDate.now().toEpochDay(),
        height = height,
        weight = weight,
        gender = gender?.stringResId,
        activityLevel = activityLevel?.stringResId,
        dietGoal = dietGoal?.stringResId,
        kcalGoal = kcalGoal
    )
}