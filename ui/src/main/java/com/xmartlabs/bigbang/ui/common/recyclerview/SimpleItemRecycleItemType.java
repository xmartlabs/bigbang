package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

/**
 * Simple {@link RecycleItemType} implementation with an empty {@code onBindViewHolder} implementation.
 *
 * @param <I>  The item related to the {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 * @param <VH> The {@link android.support.v7.widget.RecyclerView.ViewHolder} related to the item.
 */
public abstract class SimpleItemRecycleItemType<I, VH extends SingleItemBaseViewHolder<I>>
    implements RecycleItemType<I, VH> {
  @Override
  public void onBindViewHolder(@NonNull VH viewHolder, @NonNull I item, int position) {

  }
}
