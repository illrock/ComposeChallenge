package me.illrock.composechallenge.presentation.feed

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import me.illrock.composechallenge.R
import me.illrock.composechallenge.data.repository.FeedRepository
import me.illrock.composechallenge.presentation.feed.FeedContract.Presenter
import me.illrock.composechallenge.presentation.feed.FeedContract.View
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.ImageTitleDescriptionCardViewObject
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.TextCardViewObject
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.TitleDescriptionCardViewObject
import me.illrock.composechallenge.utils.print
import moxy.MvpPresenter
import javax.inject.Inject

class FeedPresenter @Inject constructor(
    private val feedRepository: FeedRepository
) : MvpPresenter<View>(), Presenter {
    override fun onFirstViewAttach() = updateFeed(false)
    override fun onPullRefresh() = updateFeed(true)

    private fun updateFeed(isForce: Boolean) {
        feedRepository.get(isForce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { viewState.showContent(it.page.cards) },
                {
                    it.print()
                    viewState.showError(R.string.feed_error_loading)
                }
            )
    }

    override fun onCardClick(itemViewObject: Any) {
        //todo implement proper onclick, have no action in json response
        val message = when (itemViewObject) {
            is TextCardViewObject -> itemViewObject.text
            is TitleDescriptionCardViewObject -> "${itemViewObject.title}\n${itemViewObject.description}"
            is ImageTitleDescriptionCardViewObject -> itemViewObject.imageUrl
            else -> null
        }
        message?.let { viewState.showToast(it) }
    }
}