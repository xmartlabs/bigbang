package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

/**
 * Used to automatically bind an item with a view holder
 *
 * @param <T> Type of the item.
 */
@SuppressWarnings("WeakerAccess")
public interface BindingItemViewHolder<T> {
  /**
   * Binds an item with their content.
   *
   * @param item The item to bind.
   */
  void bindItem(@NonNull T item);
}
