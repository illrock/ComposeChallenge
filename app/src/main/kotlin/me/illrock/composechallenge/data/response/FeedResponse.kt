package me.illrock.composechallenge.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.illrock.composechallenge.data.entity.feed.card.BaseCard

@JsonClass(generateAdapter = true)
data class FeedResponse(
    @Json(name = "page")
    val page: Page
) {
    @JsonClass(generateAdapter = true)
    data class Page(
        @Json(name = "cards")
        val cards: List<BaseCard>
    )
}