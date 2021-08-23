package me.illrock.composechallenge.presentation.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import me.illrock.composechallenge.BuildConfig
import me.illrock.composechallenge.data.network.ApiService
import me.illrock.composechallenge.data.network.adapter.FeedCardAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(FeedCardAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: OkHttpClient, moshi: Moshi): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePicasso(context: Context, okHttpClient: OkHttpClient): Picasso {
        val picassoHttpClient = okHttpClient.newBuilder()
            .apply {
                interceptors().clear()
                cache(Cache(context.cacheDir, PICASSO_CACHE_SIZE))
            }.build()
        val picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(picassoHttpClient))
            .build()

        try {
            Picasso.setSingletonInstance(picasso)
        } catch (e: IllegalStateException) {
        }

        return picasso
    }

    companion object {
        private const val CONNECT_TIMEOUT = 15L
        private const val READ_TIMEOUT = 70L
        private const val PICASSO_CACHE_SIZE: Long = 128 * 1024 * 1024
    }
}