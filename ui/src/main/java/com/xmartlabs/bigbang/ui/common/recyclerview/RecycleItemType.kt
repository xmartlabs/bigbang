package com.xmartlabs.bigbang.ui.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * A recycler view item, which relates an item [I] with a [android.support.v7.widget.RecyclerView.ViewHolder].
 *
 * @param <I>  Item type.
 * *
 * @param <VH> View holder type.
 */
interface RecycleItemType<in I : Any, VH : RecyclerView.ViewHolder> {
  /**
   * Called to crate a new view holder of the type [VH].
   *
   * @param parent the parent view of the view holder.
   * *
   * @return A [android.support.v7.widget.RecyclerView.ViewHolder] of the type [VH].
   */
  fun onCreateViewHolder(parent: ViewGroup): VH

  /**
   * Called to bind the [android.support.v7.widget.RecyclerView.ViewHolder] [VH] with the item of the ype [I]
   *
   * @param viewHolder [android.support.v7.widget.RecyclerView.ViewHolder] to be bound.
   * *
   * @param item       Item to be bound.
   * *
   * @param position   Position in the adapter.
   */
  fun onBindViewHolder(viewHolder: VH, item: I, position: Int)
}
