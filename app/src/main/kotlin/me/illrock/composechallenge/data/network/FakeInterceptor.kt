package me.illrock.composechallenge.data.network

import me.illrock.composechallenge.BuildConfig
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.app.App
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FakeInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        return if (BuildConfig.DEBUG && chain.request().url.encodedPath.contains("test/home")) {

            val inputStream = App.appComponent.applicationContext().resources.openRawResource(R.raw.feed_response)
            val response = BufferedReader(InputStreamReader(inputStream)).use(BufferedReader::readText)

            Response.Builder()
                .code(200)
                .message(response)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(response.toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            chain.proceed(chain.request())
        }
    }
}