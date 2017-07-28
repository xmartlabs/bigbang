package com.xmartlabs.bigbang.ui.common.recyclerview

/**
 * [RecycleItemType] subclass with an empty `onBindViewHolder` implemented.

 * @param <I>  The item related to the [android.support.v7.widget.RecyclerView.ViewHolder].
 * *
 * @param <VH> The actual item to be displayed in the [android.support.v7.widget.RecyclerView.ViewHolder].
 */
abstract class SimpleItemRecycleItemType<in I : Any, VH : SingleItemBaseViewHolder<I>> : RecycleItemType<I, VH> {
  override fun onBindViewHolder(viewHolder: VH, item: I, position: Int) { }
}
