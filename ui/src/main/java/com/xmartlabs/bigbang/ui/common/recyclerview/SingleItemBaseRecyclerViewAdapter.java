package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * A Base RecyclerViewAdapter with already implemented functions such as
 * Setting, removing, adding items, getting its count among others.
 */
public abstract class SingleItemBaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
    extends BaseRecyclerViewAdapter implements RecycleItemType<T, VH> {
  /**
   * Sets the items data for the recycler view and notifying any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be the data for the recycler view
   */
  @MainThread
  @SuppressWarnings("unused")
  public void setItems(@Nullable List<? extends T> items) {
    setItems(this, items);
  }

  /**
   * Sets the items data for the recycler view in form of an array and calls {@link #setItems(List)}
   *
   * @param items array of items that will be the data for the recycler view
   */
  @MainThread
  @SuppressWarnings("unused")
  public void setItems(@NonNull T[] items) {
    setItems(Arrays.asList(items));
  }

  /**
   * Adds items to the data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be the data for the recycler view
   * @return if item was successfully added
   */
  @MainThread
  @SuppressWarnings("unused")
  public boolean addItems(@Nullable List<? extends T> items) {
    return addItems(this, items);
  }
}
