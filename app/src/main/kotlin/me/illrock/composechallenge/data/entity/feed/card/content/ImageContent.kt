package me.illrock.composechallenge.data.entity.feed.card.content

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageContent(
    @Json(name = "url")
    val url: String,
    @Json(name = "size")
    val size: ImageSize
) {
    @JsonClass(generateAdapter = true)
    data class ImageSize(
        @Json(name = "width")
        val width: Int,
        @Json(name = "height")
        val height: Int
    )
}