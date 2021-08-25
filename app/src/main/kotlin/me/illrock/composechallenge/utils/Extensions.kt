package me.illrock.composechallenge.utils

import android.content.res.Resources
import com.squareup.moshi.Moshi
import me.illrock.composechallenge.BuildConfig

fun Throwable.print() = if (BuildConfig.DEBUG) printStackTrace() else Unit

inline fun <reified T> Moshi.fromJson(json: String): T? = try {
    adapter(T::class.java).fromJson(json)
} catch (e: Exception) {
    null
}

inline fun <reified T> Moshi.toJson(item: T): String = adapter(T::class.java).toJson(item)

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()