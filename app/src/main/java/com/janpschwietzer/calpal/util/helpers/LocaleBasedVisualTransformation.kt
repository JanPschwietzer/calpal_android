package com.janpschwietzer.calpal.util.helpers

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormatSymbols
import java.util.Locale

class LocaleBasedVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text.text.replace('.', DecimalFormatSymbols.getInstance(Locale.getDefault()).decimalSeparator)
        return TransformedText(AnnotatedString(transformedText), OffsetMapping.Identity)
    }
}

