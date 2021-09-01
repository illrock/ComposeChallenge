package me.illrock.composechallenge.presentation.feed.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import me.illrock.composechallenge.R
import me.illrock.composechallenge.data.entity.feed.card.BaseCard
import me.illrock.composechallenge.data.repository.FeedRepository
import me.illrock.composechallenge.utils.print
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _cards = MutableLiveData<List<BaseCard>>(listOf())
    val cards: LiveData<List<BaseCard>> = _cards

    private val _errorRes = MutableLiveData<Int?>()
    val errorRes: LiveData<Int?> = _errorRes

    private var feedDisposable = Disposable.disposed()

    fun updateFeed(isForce: Boolean) {
        feedDisposable.dispose()
        feedDisposable = feedRepository.get(isForce)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (_cards.value?.isNullOrEmpty() == true) _isLoading.value = true
                else _isRefreshing.value = true
                _errorRes.value = null
            }.subscribe({
                _cards.value = it.page.cards
                _isLoading.value = false
                _isRefreshing.value = false
            }, {
                it.print()
                _isLoading.value = false
                _isRefreshing.value = false
                _errorRes.value = R.string.feed_error_loading
            })
    }

    override fun onCleared() {
        feedDisposable.dispose()
    }
}