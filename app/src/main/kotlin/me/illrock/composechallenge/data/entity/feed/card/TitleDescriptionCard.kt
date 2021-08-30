package me.illrock.composechallenge.data.entity.feed.card

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent
import me.illrock.composechallenge.presentation.feed.mvp.adapter.viewholder.TitleDescriptionCardViewObject
import me.illrock.composechallenge.presentation.safeParseColor

@JsonClass(generateAdapter = true)
data class TitleDescriptionCard(
    @Json(name = "card")
    val card: Content
) : BaseCard {
    @JsonClass(generateAdapter = true)
    data class Content(
        @Json(name = "title")
        val title: TextContent,
        @Json(name = "description")
        val description: TextContent
    )

    override fun toViewObject() = TitleDescriptionCardViewObject(
        card.title.value,
        safeParseColor(card.title.attributes.textColor),
        card.title.attributes.font.size,
        card.description.value,
        safeParseColor(card.description.attributes.textColor),
        card.description.attributes.font.size
    )
}