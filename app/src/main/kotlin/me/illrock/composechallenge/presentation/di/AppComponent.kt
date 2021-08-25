package me.illrock.composechallenge.presentation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.illrock.composechallenge.presentation.feed.FeedPresenter
import me.illrock.composechallenge.presentation.main.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun applicationContext(): Context
    fun mainPresenter(): MainPresenter
    fun feedPresenter(): FeedPresenter
}