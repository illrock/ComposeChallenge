package me.illrock.composechallenge.presentation.main

import me.illrock.composechallenge.presentation.main.MainContract.View
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
) : MvpPresenter<View>(), MainContract.Presenter {
    override fun onRecyclerClick() = viewState.showImplementation(false)
    override fun onComposeClick() = viewState.showImplementation(true)
}