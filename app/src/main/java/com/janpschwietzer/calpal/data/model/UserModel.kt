package com.janpschwietzer.calpal.data.model

import com.janpschwietzer.calpal.data.source.local.UserEntity
import com.janpschwietzer.calpal.util.enums.ActivityLevel
import com.janpschwietzer.calpal.util.enums.DietGoal
import com.janpschwietzer.calpal.util.enums.Gender
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import java.time.LocalDate

data class UserModel(
    var name: String = "Musterperson",
    var birthdate: LocalDate = LocalDate.now(),
    var height: Int = 170,
    var weight: Int = 70,
    var gender: Gender = Gender.MALE,
    var activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    var dietGoal: DietGoal = DietGoal.MAINTAIN,
    var kcalGoal: Int = 2000
)

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        name = name,
        birthdate = LocalDateConverter.toTimestamp(birthdate) ?: LocalDate.now().toEpochDay(),
        height = height,
        weight = weight,
        gender = gender.stringResId,
        activityLevel = activityLevel.stringResId,
        dietGoal = dietGoal.stringResId,
        kcalGoal = kcalGoal
    )
}