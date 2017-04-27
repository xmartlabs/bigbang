package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public interface BindingItemViewHolder<T> {
  void bindItem(@NonNull T item);
}
