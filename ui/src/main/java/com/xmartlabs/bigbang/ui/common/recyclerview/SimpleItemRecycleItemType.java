package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;

/**
 * {@link RecycleItemType} subclass with an empty {@code onBindViewHolder} implemented.
 *
 * @param <I>  The item related to the {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 * @param <VH> The actual item to be displayed in the {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 */
public abstract class SimpleItemRecycleItemType<I, VH extends SingleItemBaseViewHolder<I>>
    implements RecycleItemType<I, VH> {
  @Override
  public void onBindViewHolder(@NonNull VH viewHolder, @NonNull I item, int position) {

  }
}
