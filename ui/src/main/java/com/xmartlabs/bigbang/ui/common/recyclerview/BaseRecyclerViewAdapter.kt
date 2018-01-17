package com.xmartlabs.bigbang.ui.common.recyclerview

import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.MainThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

/**
 * A Base RecyclerViewAdapter with already implemented functions such as
 * Setting, removing, adding items, getting its count among others.
 */
abstract class BaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  protected val items = ArrayList<Element>()
  private val types = ArrayList<RecycleItemType<*, *>>()
  private var updateElementsDisposable: Disposable? = null

  open protected class Element(var type: RecycleItemType<*, *>, var item: Any)

  protected fun inflateView(parent: ViewGroup, @LayoutRes layoutResId: Int): View {
    val layoutInflater = LayoutInflater.from(parent.context)
    return layoutInflater.inflate(layoutResId, parent, false)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      types[viewType].onCreateViewHolder(parent)

  /**
   * Removes an item from the data and any registered observers of its removal.
   *
   * @param item the item to be removed.
   */
  @MainThread
  fun removeItem(item: Any) {
    items.withIndex()
        .filter { it.value.item == item }
        .sortedByDescending { it.index }
        .forEach {
          items.removeAt(it.index)
          notifyItemRemoved(it.index)
        }
  }

  /**
   * Removes an item from the data and any registered observers of its removal.
   *
   * @param index the index of the item to be removed.
   */
  @MainThread
  fun removeItemAtIndex(index: Int) {
    if (index < items.size) {
      items.removeAt(index)
      notifyItemRemoved(index)
    }
  }

  /**
   * Removes a list of items from the data and any registered observers of its removal.
   *
   * @param items the list of items to be removed.
   */
  @MainThread
  fun removeItems(items: List<Any>) = items.distinct().forEach(this::removeItem)

  /**
   * Adds an item to the data for the recycler view without notifying any registered observers that an item has been
   * added.
   *
   * @param type            The type of the item.
   * *
   * @param item            The item to be added.
   * *
   * @param addTypeIfNeeded A boolean specifying if the item type has to be added to the type collection.
   * *                        If this parameter is true, the type will be added only if it wasn't added yet.
   */
  protected fun <T : RecycleItemType<*, *>> addItemWithoutNotifying(type: T, item: Any,
                                                                    addTypeIfNeeded: Boolean) {
    addItemWithoutNotifying(items.size, type, item, addTypeIfNeeded)
  }

  /**
   * Adds an item to the data for the recycler view without notifying any registered observers that an item has been
   * added.
   *
   * @param index           The index at which the specified items are to be inserted.
   * *
   * @param type            The type of the item.
   * *
   * @param item            The item to be added.
   * *
   * @param addTypeIfNeeded A boolean specifying if the item type has to be added to the type collection.
   * *                        If this parameter is true, the type will be added only if it wasn't added yet.
   */
  private fun <T : RecycleItemType<*, *>> addItemWithoutNotifying(index: Int, type: T,
                                                                  item: Any,
                                                                  addTypeIfNeeded: Boolean) {
    val element = Element(type, item)
    items.add(index, element)
    if (addTypeIfNeeded) {
      addItemTypeIfNeeded(type)
    }
  }

  /**
   * Add the type to the collection type only if it is needed.
   *
   * @param type The type to be added.
   */
  private fun <T : RecycleItemType<*, *>> addItemTypeIfNeeded(type: T) {
    if (!types.contains(type)) {
      types.add(type)
    }
  }

  /**
   * Adds an item to the data for the recycler view and notifies any registered observers that an item has been added.
   *
   * @param type The type of the item.
   * *
   * @param item The item to be added.
   */
  @MainThread
  protected fun <T : RecycleItemType<*, *>> addItem(type: T, item: Any) {
    addItemWithoutNotifying(type, item, true)
    notifyItemInserted(items.size - 1)
  }

  /**
   * Adds items to the data for the recycler view and notifies any registered observers that the items has been added.
   *
   * @param index The index at which the specified items are to be inserted.
   * *
   * @param type  The type of the item.
   * *
   * @param items The items that will be the data for the recycler view.
   * *
   * @return if items was successfully added.
   */
  @MainThread
  protected fun <T : RecycleItemType<*, *>> addItems(index: Int, type: T, items: List<*>?): Boolean {
    if (items == null || items.isEmpty()) {
      return false
    }
    val lastItemCount = itemCount
    items.filterNotNull().forEachIndexed { pos, element -> addItemWithoutNotifying(pos + index, type, element, false) }
    addItemTypeIfNeeded(type)
    notifyItemRangeInserted(index, itemCount - lastItemCount)
    return true
  }

  /**
   * Adds items to the data for the recycler view and notifies any registered observers that the items has been added.
   *
   * @param type  The type of the items.
   * *
   * @param items the items that will be the data for the recycler view.
   * *
   * @return if item was successfully added.
   */
  @MainThread
  protected fun <T : RecycleItemType<*, *>> addItems(type: T, items: List<Any>): Boolean {
    if (items.isEmpty()) {
      return false
    }

    val lastItemCount = itemCount
    items.forEach { addItemWithoutNotifying(type, it, false) }
    addItemTypeIfNeeded(type)

    if (lastItemCount == 0) {
      notifyDataSetChanged()
    } else {
      notifyItemRangeInserted(lastItemCount, itemCount - lastItemCount)
    }

    return true
  }

  /**
   * Sets the items data for the recycler view and notifying any registered observers that the data set has
   * changed. It uses a function that calculates the difference between the old and the new items
   * in order to improve the update process.
   *
   * Items compare will be executed on a secondary thread
   *
   * @param type                      Type of items.
   * *
   * @param newItems                  The items tobe set.
   * *
   * @param areItemsTheSame   A function which checks that two items are the same.
   * *
   * @param areContentTheSame A function which checks that the content of two items are the same.
   */
  protected fun <T : RecycleItemType<*, *>> setItems(type: T, newItems: List<*>,
                                                     areItemsTheSame: (Any, Any) -> Boolean,
                                                     areContentTheSame: (Any, Any) -> Boolean) {
    if (newItems.isEmpty()) {
      clearAll()
      return
    }

    if (updateElementsDisposable != null && !updateElementsDisposable!!.isDisposed) {
      updateElementsDisposable!!.dispose()
    }
    updateElementsDisposable = Single
        .fromCallable { DiffUtil.calculateDiff(getUpdateDiffCallback(newItems, areItemsTheSame, areContentTheSame)) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { diffResult ->
          items.clear()
          newItems.filterNotNull().forEach { addItemWithoutNotifying(type, it, false) }
          addItemTypeIfNeeded(type)
          diffResult.dispatchUpdatesTo(this)
        }
  }

  private fun getUpdateDiffCallback(
      newItems: List<*>,
      areItemsTheSame: (Any, Any) -> Boolean,
      areContentTheSame: (Any, Any) -> Boolean): DiffUtil.Callback {
    return object : DiffUtil.Callback() {
      override fun getOldListSize() = items.size

      override fun getNewListSize() = newItems.size

      override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
          areItemsTheSame(items[oldItemPosition].item, newItems[newItemPosition]!!)

      override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
          areContentTheSame(items[oldItemPosition].item, newItems[newItemPosition]!!)
    }
  }

  /**
   * Sets the items data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param type     Type of items.
   * *
   * @param newItems The items tobe added.
   */
  @MainThread
  protected fun <T : RecycleItemType<*, *>> setItems(type: T, newItems: List<Any>) {
    items.clear()
    addItems(type, newItems)
  }

  /**
   * Gets all the items count, including dividers.
   *
   * @return number of total items.
   */
  override fun getItemCount(): Int = items.size

  @CallSuper
  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
    val element = items[position]
    val item = element.item

    @Suppress("UNCHECKED_CAST")
    (element.type as? RecycleItemType<Any, RecyclerView.ViewHolder>)?.onBindViewHolder(viewHolder, item, position)
    @Suppress("UNCHECKED_CAST")
    (viewHolder as? BindingItemViewHolder<Any>)?.bindItem(element.item)
  }

  /**
   * Gets the item type.
   *
   * @param position of the item among all items conforming the recycler view.
   * *
   * @return item divider type.
   */
  override fun getItemViewType(position: Int) = types.indexOf(items[position].type)

  /** Removes all items and notifies that the data has changed.  */
  @MainThread
  fun clearAll() {
    items.clear()
    notifyDataSetChanged()
  }

  /**
   * Removes all items in the recyclerView of a specified type.
   *
   * @param itemType The specified type of items to be removed.
   */
  @MainThread
  fun clearItems(itemType: RecycleItemType<Any, RecyclerView.ViewHolder>) {
    val itemsToRemove = items
        .filter { it.type == itemType }
        .map(Element::item)

    removeItems(itemsToRemove)
  }
}
