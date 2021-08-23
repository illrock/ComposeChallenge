package me.illrock.composechallenge.data.entity.feed.card.content

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TextContent(
    @Json(name = "value")
    val value: String,
    @Json(name = "attributes")
    val attributes: Attributes
) {
    @JsonClass(generateAdapter = true)
    data class Attributes(
        @Json(name = "text_color")
        val textColor: String,
        @Json(name = "font")
        val font: TextFont
    ) {
        @JsonClass(generateAdapter = true)
        data class TextFont(
            @Json(name = "size")
            val size: Int
        )
    }
}