package me.illrock.composechallenge.data.network

import io.reactivex.rxjava3.core.Single
import me.illrock.composechallenge.data.response.FeedResponse
import retrofit2.http.GET

interface ApiService {
    @GET("test/home")
    fun getFeed(): Single<FeedResponse>
}