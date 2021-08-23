package me.illrock.composechallenge.data.network.adapter

import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import me.illrock.composechallenge.data.entity.feed.card.BaseCard
import me.illrock.composechallenge.data.entity.feed.card.ImageTitleDescriptionCard
import me.illrock.composechallenge.data.entity.feed.card.TextCard
import me.illrock.composechallenge.data.entity.feed.card.TitleDescriptionCard

class FeedCardAdapterFactory {
    companion object {
        fun create(): PolymorphicJsonAdapterFactory<BaseCard> = PolymorphicJsonAdapterFactory
            .of(BaseCard::class.java, BaseCard.CARD_TYPE_NAME)
            .withSubtype(TextCard::class.java, "text")
            .withSubtype(TitleDescriptionCard::class.java, "title_description")
            .withSubtype(ImageTitleDescriptionCard::class.java, "image_title_description")
    }
}