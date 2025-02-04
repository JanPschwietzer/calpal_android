package com.janpschwietzer.calpal.util.enums

import android.content.Context
import com.janpschwietzer.calpal.R

enum class MealTime(private val stringResId: Int, val factor: Double) {
    BREAKFAST(R.string.mealtime_breakfast, 0.25),
    LUNCH(R.string.mealtime_lunch, 0.35),
    DINNER(R.string.mealtime_dinner, 0.30),
    SNACK(R.string.mealtime_snacks, 0.10);

    fun getDisplayedString(context: Context): String {
        return context.getString(stringResId)
    }

    companion object {
        fun fromId(id: Int?): MealTime {
            return when (id) {
                BREAKFAST.ordinal -> BREAKFAST
                LUNCH.ordinal -> LUNCH
                DINNER.ordinal -> DINNER
                SNACK.ordinal -> SNACK
                else -> BREAKFAST
            }
        }
    }
}