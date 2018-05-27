package com.xmartlabs.bigbang.ui.common.recyclerview

/**
 * Used to automatically bind an item with a view holder
 *
 * @param <T> Type of the item.
 */
interface BindingItemViewHolder<in T> {
  /**
   * Binds an item with their content.
   *
   * @param item The item to bind.
   */
  fun bindItem(item: T)
}
