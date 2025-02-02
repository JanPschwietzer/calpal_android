package com.janpschwietzer.calpal.util.enums

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.janpschwietzer.calpal.R

enum class PortionUnit {
    METRICAL,
    PORTION;

    fun getDisplayedString(context: Context): String {
        return when (this) {
            METRICAL -> context.getString(R.string.unit_metrical)
            PORTION -> context.getString(R.string.unit_portion)
        }
    }

    companion object {
        fun fromId(id: Int): PortionUnit {
            return when (id) {
                METRICAL.ordinal -> METRICAL
                PORTION.ordinal -> PORTION
                else -> METRICAL
            }
        }
    }
}