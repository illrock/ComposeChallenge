package me.illrock.composechallenge.data.entity.feed.card

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.illrock.composechallenge.data.entity.feed.card.content.ImageContent
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.ImageTitleDescriptionCardViewObject
import me.illrock.composechallenge.presentation.safeParseColor

@JsonClass(generateAdapter = true)
data class ImageTitleDescriptionCard(
    @Json(name = "card")
    val content: Content
) : BaseCard {
    @JsonClass(generateAdapter = true)
    data class Content(
        @Json(name = "image")
        val image: ImageContent,
        @Json(name = "title")
        val title: TextContent,
        @Json(name = "description")
        val description: TextContent
    )

    override fun toViewObject() = ImageTitleDescriptionCardViewObject(
        content.title.value,
        safeParseColor(content.title.attributes.textColor),
        content.title.attributes.font.size,
        content.description.value,
        safeParseColor(content.description.attributes.textColor),
        content.description.attributes.font.size,
        content.image.url,
        content.image.size.width,
        content.image.size.height
    )
}