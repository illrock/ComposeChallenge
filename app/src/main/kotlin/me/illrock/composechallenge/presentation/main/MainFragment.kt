package me.illrock.composechallenge.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.app.App
import me.illrock.composechallenge.presentation.feed.FeedComposeFragment
import me.illrock.composechallenge.presentation.feed.FeedRecyclerFragment
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class MainFragment : MvpAppCompatFragment(R.layout.fragment_main), MainContract.View {
    private val presenter by moxyPresenter {
        App.appComponent.mainPresenter()
    }

    private lateinit var btnRecycler: View
    private lateinit var btnCompose: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViews()
        setListeners()
    }

    private fun View.findViews() {
        btnRecycler = findViewById(R.id.btnRecycler)
        btnCompose = findViewById(R.id.btnCompose)
    }

    private fun setListeners() {
        btnRecycler.setOnClickListener { presenter.onRecyclerClick() }
        btnCompose.setOnClickListener { presenter.onComposeClick() }
    }

    override fun showImplementation(isCompose: Boolean) {
        val fragment = if (isCompose) FeedComposeFragment()
        else FeedRecyclerFragment()
        showFragment(fragment)
    }

    private fun showFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.flFragmentContainer, fragment)
            addToBackStack(null)
        }
    }
}