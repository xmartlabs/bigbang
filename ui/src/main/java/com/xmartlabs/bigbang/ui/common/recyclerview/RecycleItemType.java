package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * A recycler view item, which relate an item {@link I} with a {@link android.support.v7.widget.RecyclerView.ViewHolder}
 * {@link VH}.
 *
 * @param <I>  Item type.
 * @param <VH> View holder type.
 */
public interface RecycleItemType<I, VH extends RecyclerView.ViewHolder> {
  /**
   * Its called to crate a new view holder of the type {@link VH}.
   *
   * @param parent the view parent of the view holder.
   * @return A {@link android.support.v7.widget.RecyclerView.ViewHolder} of the type {@link VH}.
   */
  @NonNull
  VH onCreateViewHolder(@NonNull ViewGroup parent);
  /**
   * It's called to bind the {@link android.support.v7.widget.RecyclerView.ViewHolder} {@link VH} with the item of the
   * type {@link I}
   *
   * @param viewHolder {@link android.support.v7.widget.RecyclerView.ViewHolder} to be bound.
   * @param item       Item to be to be bound.
   * @param position   Position in the adapter.
   */
  void onBindViewHolder(@NonNull VH viewHolder, @NonNull I item, int position);
}
