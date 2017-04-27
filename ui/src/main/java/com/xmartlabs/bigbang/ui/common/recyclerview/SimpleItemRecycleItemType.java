package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

public abstract class SimpleItemRecycleItemType<I, VH extends SingleItemBaseViewHolder<I>>
    implements RecycleItemType<I, VH> {
  public void onBindViewHolder(@NonNull VH viewHolder, @NonNull I item, int position) {
    viewHolder.bindItem(item);
  }
}
