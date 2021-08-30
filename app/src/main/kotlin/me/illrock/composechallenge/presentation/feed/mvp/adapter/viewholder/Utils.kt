package me.illrock.composechallenge.presentation.feed.mvp.adapter.viewholder

import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import me.illrock.composechallenge.R

fun TextView.bindCardText(
    text: String,
    @ColorInt color: Int?,
    size: Int,
    @ColorRes fallbackColor: Int = R.color.text_title
) {
    this.text = text
    val textColor = color ?: ContextCompat.getColor(context, fallbackColor)
    setTextColor(textColor)
    setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
}