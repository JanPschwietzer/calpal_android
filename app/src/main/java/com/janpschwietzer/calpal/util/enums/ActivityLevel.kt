package com.janpschwietzer.calpal.util.enums

import android.content.Context
import com.janpschwietzer.calpal.R

enum class ActivityLevel(val stringResId: Int, val factor: Double) {
    VERYLOW(R.string.activitylevel_very_low, 1.2),
    LOW(R.string.activitylevel_low, 1.375),
    MODERATE(R.string.activitylevel_moderate, 1.55),
    HIGH(R.string.activitylevel_high, 1.725),
    VERYHIGH(R.string.activitylevel_very_high, 1.9);

    fun getDisplayedString(context: Context): String {
        return context.getString(stringResId)
    }

    companion object {
        fun fromId(id: Int): ActivityLevel {
            return when (id) {
                VERYLOW.stringResId -> VERYLOW
                LOW.stringResId -> LOW
                MODERATE.stringResId -> MODERATE
                HIGH.stringResId -> HIGH
                VERYHIGH.stringResId -> VERYHIGH
                else -> MODERATE
            }
        }
    }
}
