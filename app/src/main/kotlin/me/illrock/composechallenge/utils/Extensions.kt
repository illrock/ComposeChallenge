package me.illrock.composechallenge.utils

import com.squareup.moshi.Moshi
import me.illrock.composechallenge.BuildConfig

fun Throwable.print() = if (BuildConfig.DEBUG) printStackTrace() else Unit

inline fun <reified T> Moshi.fromJson(json: String): T? = adapter(T::class.java).fromJson(json)
inline fun <reified T> Moshi.toJson(item: T): String = adapter(T::class.java).toJson(item)