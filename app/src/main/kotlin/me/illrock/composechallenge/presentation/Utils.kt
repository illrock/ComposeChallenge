package me.illrock.composechallenge.presentation

import android.graphics.Color
import androidx.annotation.ColorInt

@ColorInt
fun safeParseColor(rawColor: String): Int? {
    return try {
        Color.parseColor(rawColor)
    } catch (e: java.lang.Exception) {
        null
    }
}