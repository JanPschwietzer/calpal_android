package com.janpschwietzer.calpal.util.enums

import android.content.Context
import com.janpschwietzer.calpal.R

enum class DietGoal(val stringResId: Int, val factor: Double) {
    LOOSE(R.string.dietgoal_loose, 0.9),
    MAINTAIN(R.string.dietgoal_maintain, 1.0),
    GAIN(R.string.dietgoal_gain, 1.1);

    fun getDisplayedString(context: Context): String {
        return context.getString(stringResId)
    }

    companion object {
        fun fromId(id: Int): DietGoal {
            return when (id) {
                LOOSE.stringResId -> LOOSE
                MAINTAIN.stringResId -> MAINTAIN
                GAIN.stringResId -> GAIN
                else -> MAINTAIN
            }
        }
    }
}