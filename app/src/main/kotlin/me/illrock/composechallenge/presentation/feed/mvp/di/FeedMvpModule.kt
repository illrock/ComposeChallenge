package me.illrock.composechallenge.presentation.feed.mvp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import me.illrock.composechallenge.data.repository.FeedRepository
import me.illrock.composechallenge.presentation.feed.mvp.FeedPresenter

@InstallIn(FragmentComponent::class)
@Module
object FeedMvpModule {
    @Provides
    fun feedPresenter(repository: FeedRepository) = FeedPresenter(repository)
}