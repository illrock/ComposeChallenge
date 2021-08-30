package me.illrock.composechallenge.presentation.feed.mvp.adapter.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.squareup.picasso.Picasso
import me.illrock.composechallenge.R
import me.illrock.composechallenge.presentation.base.adapter.BaseViewHolder

class ImageTitleDescriptionCardViewHolder(parent: ViewGroup): BaseViewHolder<ImageTitleDescriptionCardViewObject>(parent) {
    override val content = R.layout.item_image_title_description_card

    private val tvTitle: TextView = findViewById(R.id.tvTitle)
    private val tvDescription: TextView = findViewById(R.id.tvDescription)
    private val ivBackground: ImageView = findViewById(R.id.ivBackground)

    override fun onBind(item: ImageTitleDescriptionCardViewObject) {
        tvTitle.bindCardText(item.title, item.titleColor, item.titleSize)
        tvDescription.bindCardText(item.description, item.descriptionColor, item.descriptionSize)
        loadImage(item.imageUrl, item.imageWidth, item.imageHeight)
        itemView.setOnClickListener { listener?.invoke(item, bundleOf()) }
    }

    private fun loadImage(url: String, width: Int, height: Int) {
        Picasso.get().cancelRequest(ivBackground)
        Picasso.get().load(url)
            .resize(width, height)
            .into(ivBackground)
    }
}

data class ImageTitleDescriptionCardViewObject(
    val title: String,
    val titleColor: Int?,
    val titleSize: Int,
    val description: String,
    val descriptionColor: Int?,
    val descriptionSize: Int,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)