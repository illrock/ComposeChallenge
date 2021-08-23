package me.illrock.composechallenge.presentation.app

import android.app.Application
import me.illrock.composechallenge.presentation.di.AppComponent
import me.illrock.composechallenge.presentation.di.DaggerAppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }
}