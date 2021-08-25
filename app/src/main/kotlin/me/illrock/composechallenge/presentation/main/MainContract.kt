package me.illrock.composechallenge.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

class MainContract {
    @StateStrategyType(OneExecutionStateStrategy::class)
    interface View: MvpView {
        fun showImplementation(isCompose: Boolean)
    }

    interface Presenter {
        fun onRecyclerClick()
        fun onComposeClick()
    }
}