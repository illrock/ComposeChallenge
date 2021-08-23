package me.illrock.composechallenge.data.entity.feed.card

interface BaseCard {
    fun toViewObject() : Any

    companion object {
        const val CARD_TYPE_NAME = "card_type"
    }
}