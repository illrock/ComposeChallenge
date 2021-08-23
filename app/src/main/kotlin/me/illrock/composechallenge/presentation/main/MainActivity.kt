package me.illrock.composechallenge.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.feed.FeedFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.flFragmentContainer, FeedFragment())
            }
        }
    }
}