package me.illrock.composechallenge.presentation.feed.adapter

import android.os.Bundle
import me.illrock.composechallenge.presentation.base.adapter.BaseAdapter
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.ImageTitleDescriptionCardViewHolder
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.TextCardViewHolder
import me.illrock.composechallenge.presentation.feed.adapter.viewholder.TitleDescriptionCardViewHolder

class CardsAdapter(listener: (item: Any, payload: Bundle) -> Unit) : BaseAdapter(listener) {
    init {
        holder(::TextCardViewHolder)
        holder(::TitleDescriptionCardViewHolder)
        holder(::ImageTitleDescriptionCardViewHolder)
    }
}