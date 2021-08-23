package me.illrock.composechallenge.presentation.feed

import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Single
import me.illrock.composechallenge.R
import me.illrock.composechallenge.data.repository.FeedRepository
import me.illrock.composechallenge.data.response.FeedResponse
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.TextCardViewObject
import me.illrock.composechallenge.rule.RxTestSchedulerRule
import me.illrock.composechallenge.utils.Mocks
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class FeedPresenterTest {

    @get:Rule
    val schedulerRule = RxTestSchedulerRule()

    private lateinit var presenter: FeedPresenter

    private val feedRepository: FeedRepository = mock()
    private val viewState: `FeedContract$View$$State` = mock()
    private val viewMock: FeedContract.View = mock()

    @Before
    fun setUp() {
        val emptyPage = FeedResponse.Page(listOf())
        whenever(feedRepository.get(any()))
            .thenReturn(Single.just(FeedResponse(emptyPage)))

        presenter = FeedPresenter(feedRepository)
        presenter.setViewState(viewState)
    }

    @Test
    fun onFirstViewAttach_updatesFeed() {
        presenter.attachView(viewMock)
        schedulerRule.triggerActions()

        verify(feedRepository, times(1))
            .get(false)
        verify(viewState, times(1))
            .showContent(listOf())
        verify(viewState, never())
            .showError(R.string.feed_error_loading)
    }

    @Test
    fun onPullRefresh_updatesFeed() {
        presenter.onPullRefresh()
        schedulerRule.triggerActions()

        verify(feedRepository, times(1))
            .get(true)
        verify(viewState, times(1))
            .showContent(listOf())
        verify(viewState, never())
            .showError(R.string.feed_error_loading)
    }

    @Test
    fun onPullRefresh_updatesFeed_notEmptyResult() {
        val expectedTextCard = Mocks.getTextCardMock()
        whenever(feedRepository.get(true))
            .thenReturn(Single.just(FeedResponse(FeedResponse.Page(listOf(expectedTextCard)))))

        presenter.onPullRefresh()
        schedulerRule.triggerActions()

        verify(feedRepository, times(1))
            .get(true)
        verify(viewState, times(1))
            .showContent(listOf(expectedTextCard))
    }

    @Test
    fun onPullRefresh_error() {
        whenever(feedRepository.get(true))
            .thenReturn(Single.error(Exception("Random exception")))

        presenter.onPullRefresh()
        schedulerRule.triggerActions()

        verify(feedRepository, times(1))
            .get(true)
        verify(viewState, never())
            .showContent(listOf())
        verify(viewState, times(1))
            .showError(R.string.feed_error_loading)
    }

    @Test
    fun onCardClick() {
        val expectedText = "Some text"
        val textObject = TextCardViewObject(expectedText, 123, 10)
        presenter.onCardClick(textObject)
        verify(viewState, times(1))
            .showToast(expectedText)
    }
}