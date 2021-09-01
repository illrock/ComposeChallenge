package me.illrock.composechallenge.presentation.feed.mvvm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import me.illrock.composechallenge.data.entity.feed.card.TextCard
import me.illrock.composechallenge.data.entity.feed.card.content.TextContent
import org.junit.Rule
import org.junit.Test

class FeedComposeFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cardText() {
        composeTestRule.setContent {
            CardText(createTextContent())
        }
        composeTestRule.onNodeWithText(MOCK_TITLE)
            .assertIsDisplayed()
    }

    @Test
    fun cardList() {
        val anotherTitle = "Another title"
        val cards = listOf(
            TextCard(createTextContent()),
            TextCard(createTextContent(anotherTitle))
        )
        composeTestRule.setContent {
            CardList(cards)
        }
        composeTestRule.onNodeWithText(MOCK_TITLE)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(anotherTitle)
            .assertIsDisplayed()
    }

    /** For complex test objects better use Builder pattern */
    private fun createTextContent(title: String = MOCK_TITLE) = TextContent(
        title,
        TextContent.Attributes(
            MOCK_COLOR,
            TextContent.Attributes.TextFont(MOCK_TEXT_SIZE)
        )
    )

    companion object {
        private const val MOCK_TITLE = "Some title"
        private const val MOCK_COLOR = "#ff0000"
        private const val MOCK_TEXT_SIZE = 20
    }
}