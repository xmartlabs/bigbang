package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public interface BindingItemViewHolder<T> {
  /**
   * Binds an item with their content.
   *
   * @param item The item to bind.
   */
  void bindItem(@NonNull T item);
}
