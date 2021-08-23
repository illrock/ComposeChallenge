package me.illrock.composechallenge.presentation.feed.adapter.viewholder

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.base.adapter.BaseViewHolder

class TextCardViewHolder(val parent: ViewGroup) : BaseViewHolder<TextCardViewObject>(parent) {
    override val content = R.layout.item_text_card

    private val tvTitle: TextView = findViewById(R.id.tvTitle)

    override fun onBind(item: TextCardViewObject) {
        tvTitle.bindCardText(item.text, item.color, item.size)
        itemView.setOnClickListener { listener?.invoke(item, bundleOf()) }
    }
}

data class TextCardViewObject(
    val text: String,
    val color: Int?,
    val size: Int
)