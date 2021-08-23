package me.illrock.composechallenge.data.repository

import android.text.format.DateUtils
import com.squareup.moshi.Moshi
import io.reactivex.rxjava3.core.Single
import me.illrock.composechallenge.data.network.ApiService
import me.illrock.composechallenge.data.preference.PreferencesManager
import me.illrock.composechallenge.data.provider.SystemClockProvider
import me.illrock.composechallenge.data.response.FeedResponse
import me.illrock.composechallenge.utils.fromJson
import me.illrock.composechallenge.utils.print
import me.illrock.composechallenge.utils.toJson
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class FeedRepository @Inject constructor(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
    private val moshi: Moshi,
    private val systemClockProvider: SystemClockProvider
) {
    fun get(isForce: Boolean): Single<FeedResponse> {
        return if (isForce) getFromNetwork()
        else getFromDbOrNetwork()
    }

    private fun getFromNetwork() = apiService.getFeed()
        .doOnSuccess { updateCache(it) }
        .onErrorResumeNext {
            it.print()
            getFromDb()
        }

    private fun getFromDbOrNetwork(): Single<FeedResponse> {
        return if (isOutdated()) getFromNetwork()
        else getFromDb()
    }

    private fun getFromDb(): Single<FeedResponse> {
        // Should use database(Room) with proper entities. Using prefs for simplicity
        val cachedResponse = moshi.fromJson<FeedResponse>(preferencesManager.getString(PREF_FEED_LAST_RESPONSE))
            ?: FeedResponse(FeedResponse.Page(listOf()))
        return Single.just(cachedResponse)
    }

    private fun isOutdated(): Boolean {
        val lastUpdate = preferencesManager.getLong(PREF_FEED_LAST_UPDATE_TIME, 0L)
        return CACHE_LIFETIME < abs(systemClockProvider.elapsedRealtime() - lastUpdate)
    }

    private fun updateCache(response: FeedResponse) {
        val json = moshi.toJson(response)
        preferencesManager.putString(PREF_FEED_LAST_RESPONSE, json)
        preferencesManager.putLong(PREF_FEED_LAST_UPDATE_TIME, systemClockProvider.elapsedRealtime())
    }

    companion object {
        private const val CACHE_LIFETIME = DateUtils.HOUR_IN_MILLIS
        private const val PREF_FEED_LAST_UPDATE_TIME = "feed_last_update_time"
        private const val PREF_FEED_LAST_RESPONSE = "feed_last_response"
    }
}