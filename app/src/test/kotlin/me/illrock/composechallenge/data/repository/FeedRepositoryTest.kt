package me.illrock.composechallenge.data.repository

import android.text.format.DateUtils
import com.nhaarman.mockitokotlin2.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Single
import me.illrock.composechallenge.data.network.ApiService
import me.illrock.composechallenge.data.preference.PreferencesManager
import me.illrock.composechallenge.data.provider.SystemClockProvider
import me.illrock.composechallenge.data.response.FeedResponse
import me.illrock.composechallenge.rule.RxTestSchedulerRule
import me.illrock.composechallenge.utils.Mocks
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class FeedRepositoryTest {

    @get:Rule
    val schedulerRule = RxTestSchedulerRule()

    private lateinit var feedRepository: FeedRepository

    private val apiService: ApiService = mock()
    private val preferencesManager: PreferencesManager = mock()
    private val moshi: Moshi = mock()
    private val moshiAdapter: JsonAdapter<FeedResponse> = mock()
    private val systemClockProvider: SystemClockProvider = mock()

    @Before
    fun setUp() {
        whenever(apiService.getFeed())
            .thenReturn(Single.just(createEmptyResponse()))
        whenever(preferencesManager.getString(PREF_FEED_LAST_RESPONSE))
            .thenReturn("")
        whenever(preferencesManager.getLong(PREF_FEED_LAST_UPDATE_TIME))
            .thenReturn(0L)

        whenever(moshiAdapter.toJson(createEmptyResponse()))
            .thenReturn("")
        whenever(moshiAdapter.fromJson(""))
            .thenReturn(createEmptyResponse())
        whenever(moshi.adapter(FeedResponse::class.java))
            .thenReturn(moshiAdapter)

        feedRepository = FeedRepository(apiService, preferencesManager, moshi, systemClockProvider)
    }

    @Test
    fun get_force_success() {
        val testObserver = feedRepository.get(true).test()
        schedulerRule.triggerActions()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(createEmptyResponse())
        verify(apiService, times(1))
            .getFeed()
        verify(preferencesManager, never())
            .getString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, never())
            .getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        verify(preferencesManager, times(1))
            .putString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .putLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
    }

    @Test
    fun get_force_error() {
        whenever(apiService.getFeed())
            .thenReturn(Single.error(Exception()))
        val expectedCacheResponse = FeedResponse(FeedResponse.Page(listOf(Mocks.getTextCardMock())))
        whenever(moshiAdapter.fromJson(""))
            .thenReturn(expectedCacheResponse)

        val testObserver = feedRepository.get(true).test()
        schedulerRule.triggerActions()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(expectedCacheResponse)
        verify(apiService, times(1))
            .getFeed()
        verify(preferencesManager, times(1))
            .getString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, never())
            .getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        verify(preferencesManager, never())
            .putString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, never())
            .putLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
    }

    @Test
    fun get_cache_notOutdated() {
        val testObserver = feedRepository.get(false).test()
        schedulerRule.triggerActions()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(createEmptyResponse())
        verify(apiService, never())
            .getFeed()
        verify(preferencesManager, times(1))
            .getString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        verify(preferencesManager, never())
            .putString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, never())
            .putLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
    }

    @Test
    fun get_cache_outdated() {
        val outdatedTime = CACHE_LIFETIME + 1
        whenever(systemClockProvider.elapsedRealtime())
            .thenReturn(outdatedTime)

        val testObserver = feedRepository.get(false).test()
        schedulerRule.triggerActions()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(createEmptyResponse())
        verify(apiService, times(1))
            .getFeed()
        verify(preferencesManager, never())
            .getString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        verify(preferencesManager, times(1))
            .putString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .putLong(PREF_FEED_LAST_UPDATE_TIME, outdatedTime)
    }

    @Test
    fun get_cache_outdated_corrupted() {
        val outdatedTime = CACHE_LIFETIME + 1
        whenever(systemClockProvider.elapsedRealtime())
            .thenReturn(outdatedTime)
        whenever(preferencesManager.getString(PREF_FEED_LAST_RESPONSE))
            .thenReturn("abracadabra")

        val testObserver = feedRepository.get(false).test()
        schedulerRule.triggerActions()

        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertValue(createEmptyResponse())
        verify(apiService, times(1))
            .getFeed()
        verify(preferencesManager, never())
            .getString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        verify(preferencesManager, times(1))
            .putString(PREF_FEED_LAST_RESPONSE, "")
        verify(preferencesManager, times(1))
            .putLong(PREF_FEED_LAST_UPDATE_TIME, outdatedTime)
    }

    private fun createEmptyResponse() = FeedResponse(FeedResponse.Page(listOf()))

    companion object {
        private const val CACHE_LIFETIME = DateUtils.HOUR_IN_MILLIS
        private const val PREF_FEED_LAST_UPDATE_TIME = "feed_last_update_time"
        private const val PREF_FEED_LAST_RESPONSE = "feed_last_response"
    }
}