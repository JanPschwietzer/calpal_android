package com.janpschwietzer.calpal.util.enums

import android.content.Context
import com.janpschwietzer.calpal.R

enum class MealTime(private val stringResId: Int) {
    BREAKFAST(R.string.mealtime_breakfast),
    LUNCH(R.string.mealtime_lunch),
    DINNER(R.string.mealtime_dinner),
    SNACK(R.string.mealtime_snacks);

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