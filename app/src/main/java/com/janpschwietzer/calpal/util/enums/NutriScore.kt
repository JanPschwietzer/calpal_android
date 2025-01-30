package com.janpschwietzer.calpal.util.enums

enum class NutriScore(val rating: String) {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E");

    companion object {
        fun fromRating(rating: String): NutriScore {
            return when (rating) {
                A.rating -> A
                B.rating -> B
                C.rating -> C
                D.rating -> D
                E.rating -> E
                else -> A
            }
        }
    }
}