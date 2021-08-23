package me.illrock.composechallenge.presentation.base.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

open class BaseAdapter(listener: ((item: Any, payload: Bundle) -> Unit)? = null) : Adapter<ViewHolder>() {
    val selector = TypeSelector(listener)
    val items: MutableList<Any> = mutableListOf()

    fun setItems(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = selector.getItemViewType(items[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = selector.onCreateViewHolder(parent, viewType)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = selector.onBindViewHolder(holder, items[position])
    override fun onViewRecycled(holder: ViewHolder) = selector.onViewRecycled(holder)
    override fun onViewDetachedFromWindow(holder: ViewHolder) = selector.onViewDetachedFromWindow(holder)

    protected inline fun <reified T : Any> holder(noinline type: (ViewGroup) -> WrapperViewHolder<T>) =
        selector.addType(T::class to type)
}