package me.illrock.composechallenge.data.entity.feed.card

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent
import me.illrock.composechallenge.presentation.feed.mvp.adapter.viewholder.TextCardViewObject
import me.illrock.composechallenge.presentation.safeParseColor

@JsonClass(generateAdapter = true)
data class TextCard(
    @Json(name = "card")
    val card: TextContent,
) : BaseCard {
    override fun toViewObject() = TextCardViewObject(
        card.value,
        safeParseColor(card.attributes.textColor),
        card.attributes.font.size
    )
}