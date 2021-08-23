package me.illrock.composechallenge.utils

import me.illrock.composechallenge.data.entity.feed.card.TextCard
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent

class Mocks {
    companion object {
        fun getTextCardMock() = TextCard(
            TextContent(
                MOCK_TEXT_VALUE,
                TextContent.Attributes(
                    MOCK_TEXT_COLOR,
                    TextContent.Attributes.TextFont(MOCK_TEXT_SIZE)
                )
            )
        )

        private const val MOCK_TEXT_VALUE = "text"
        private const val MOCK_TEXT_COLOR = "#ffffff"
        private const val MOCK_TEXT_SIZE = 12
    }
}