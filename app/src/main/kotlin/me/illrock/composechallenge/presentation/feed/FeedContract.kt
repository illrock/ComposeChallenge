package me.illrock.composechallenge.presentation.feed

import androidx.annotation.StringRes
import me.illrock.composechallenge.data.entity.feed.card.BaseCard
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

class FeedContract {
    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showContent(cards: List<BaseCard>)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showError(@StringRes message: Int)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showToast(message: String)
    }

    interface Presenter {
        fun onPullRefresh()
        fun onCardClick(itemViewObject: Any)
    }
}