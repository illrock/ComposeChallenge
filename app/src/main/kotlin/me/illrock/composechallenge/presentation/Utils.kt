package me.illrock.composechallenge.presentation

import android.graphics.Color
import androidx.annotation.ColorInt
import me.illrock.composechallenge.utils.print

@ColorInt
fun safeParseColor(rawColor: String): Int? {
    return try {
        Color.parseColor(rawColor)
    } catch (e: java.lang.Exception) {
        e.print()
        null
    }
}