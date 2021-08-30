package me.illrock.composechallenge.presentation.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import me.illrock.composechallenge.presentation.main.MainPresenter

@InstallIn(FragmentComponent::class)
@Module
object MainModule {
    @Provides
    fun providePresenter() = MainPresenter()
}