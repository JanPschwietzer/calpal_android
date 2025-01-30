package com.janpschwietzer.calpal.util.enums

import android.content.Context
import com.janpschwietzer.calpal.R

enum class Gender(val stringResId: Int) {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female);

    fun getDisplayedString(context: Context): String {
        return context.getString(stringResId)
    }

    companion object {
        fun fromId(id: Int): Gender {
            return when (id) {
                MALE.stringResId -> MALE
                FEMALE.stringResId -> FEMALE
                else -> MALE
            }
        }
    }
}