package me.illrock.composechallenge.presentation.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import me.illrock.composechallenge.R
import me.illrock.composechallenge.data.entity.feed.card.BaseCard
import me.illrock.composechallenge.presentation.app.App
import me.illrock.composechallenge.presentation.feed.adapter.CardsAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FeedRecyclerFragment : MvpAppCompatFragment(R.layout.fragment_feed), FeedContract.View {
    private val presenter by moxyPresenter {
        App.appComponent.feedPresenter()
    }

    private lateinit var srlPull: SwipeRefreshLayout
    private lateinit var rvCards: RecyclerView
    private lateinit var cardsAdapter: CardsAdapter
    private lateinit var pbLoading: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViews()
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCards.layoutManager = layoutManager
        cardsAdapter = CardsAdapter { item, _ -> presenter.onCardClick(item) }
        rvCards.adapter = cardsAdapter
        srlPull.setOnRefreshListener { presenter.onPullRefresh() }
    }

    override fun showLoading() {
        if (cardsAdapter.items.isEmpty()) {
            pbLoading.isVisible = true
            srlPull.isVisible = false
        }
    }

    override fun showContent(cards: List<BaseCard>) {
        val cardViewObjects = cards.map { it.toViewObject() }
        cardsAdapter.setItems(cardViewObjects)
        // Better use DiffUtil here
        cardsAdapter.notifyDataSetChanged()
        pbLoading.isVisible = false
        srlPull.isVisible = true
        srlPull.isRefreshing = false
    }

    override fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        pbLoading.isVisible = false
        srlPull.isVisible = true
        srlPull.isRefreshing = false
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun View.findViews() {
        srlPull = findViewById(R.id.srlPull)
        rvCards = findViewById(R.id.rvCards)
        pbLoading = findViewById(R.id.pbLoading)
    }
}