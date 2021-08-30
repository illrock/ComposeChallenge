package me.illrock.composechallenge.presentation.main

import dagger.hilt.android.scopes.FragmentScoped
import me.illrock.composechallenge.presentation.main.MainContract.View
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@FragmentScoped
@InjectViewState
class MainPresenter @Inject constructor(
) : MvpPresenter<View>(), MainContract.Presenter {
    override fun onRecyclerClick() =
        viewState.showImplementation(false)
    override fun onComposeClick() =
        viewState.showImplementation(true)
}