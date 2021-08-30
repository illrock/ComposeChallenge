package me.illrock.composechallenge.presentation.feed.mvp.adapter.viewholder

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.base.adapter.BaseViewHolder

class TitleDescriptionCardViewHolder(val parent: ViewGroup): BaseViewHolder<TitleDescriptionCardViewObject>(parent) {
    override val content = R.layout.item_title_description_card

    private val tvTitle: TextView = findViewById(R.id.tvTitle)
    private val tvDescription: TextView = findViewById(R.id.tvDescription)

    override fun onBind(item: TitleDescriptionCardViewObject) {
        tvTitle.bindCardText(item.title, item.titleColor, item.titleSize)
        tvDescription.bindCardText(item.description, item.descriptionColor, item.descriptionSize)
        itemView.setOnClickListener { listener?.invoke(item, bundleOf()) }
    }
}

data class TitleDescriptionCardViewObject(
    val title: String,
    val titleColor: Int?,
    val titleSize: Int,
    val description: String,
    val descriptionColor: Int?,
    val descriptionSize: Int
)