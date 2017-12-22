package com.xmartlabs.bigbang.ui.common.recyclerview

import android.support.annotation.MainThread
import android.support.v7.widget.RecyclerView
import java.util.Arrays

/**
 * A Base RecyclerViewAdapter with already implemented functions such as
 * setting, removing, adding items, getting its count among others.
 */
abstract class SingleItemBaseRecyclerViewAdapter<T : Any, VH : RecyclerView.ViewHolder> : BaseRecyclerViewAdapter(),
    RecycleItemType<T, VH> {
  /**
   * Sets the items data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be recycler view data.
   */
  @MainThread
  fun setItems(items: List<T>) {
    setItems(this, items)
  }

  /**
   * Sets the items data for the recycler view in form of an array and calls [.setItems].
   *
   * @param items array of items that will be the data for the recycler view.
   */
  @MainThread
  fun setItems(items: Array<T>) {
    setItems(Arrays.asList(*items))
  }

  /**
   * Adds items to the recycler view.
   *
   * @param items the items that will be the recycler view data.
   * *
   * @return if item was successfully added
   */
  @MainThread
  fun addItems(items: List<T>): Boolean {
    return addItems(this, items)
  }

  /**
   * Adds an item to the recycler's view data and notifies any registered observers that an item has been added.
   *
   * @param item The item to be added.
   */
  fun addItem(item: T) {
    addItem(this, item)
  }

  /**
   * Adds a list of items to the recycler's view data and notifies any registered observers that the items has been
   * added.
   *
   * @param index The index at which the specified items are to be inserted.
   * *
   * @param items The items that will be the data for the recycler view.
   * *
   * @return if items was successfully added.
   */
  fun addItems(index: Int, items: List<T>): Boolean {
    return addItems(index, this, items)
  }

  /**
   * Sets the items for the recycler view and notifying any registered observers that the data set has
   * changed. It uses a function that calculates the difference between the old and the new items
   * in order to improve the update process.
   *
   * @param newItems The items tobe added.
   * *
   * @param areItemsTheSame A function which checks that two items are the same.
   * *
   * @param areContentTheSame A function which checks that the content of two items are the same.
   */
  protected fun setItems(newItems: List<T>, areItemsTheSame: (Any, Any) -> Boolean,
                         areContentTheSame: (Any, Any) -> Boolean) {
    setItems(this, newItems, areItemsTheSame, areContentTheSame)
  }

  override fun onBindViewHolder(viewHolder: VH, item: T, position: Int) { }
}
