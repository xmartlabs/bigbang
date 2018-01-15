package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.xmartlabs.bigbang.core.helper.function.BiFunction;

import java.util.Arrays;
import java.util.List;

/**
 * A Base RecyclerViewAdapter with already implemented functions such as
 * setting, removing, adding items, getting its count among others.
 */
public abstract class SingleItemBaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
    extends BaseRecyclerViewAdapter implements RecycleItemType<T, VH> {
  /**
   * Sets the items data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be recycler view data.
   */
  @MainThread
  @SuppressWarnings("unused")
  public void setItems(@Nullable List<? extends T> items) {
    setItems(this, items);
  }

  /**
   * Sets the items data for the recycler view in form of an array and calls {@link #setItems(List)}.
   *
   * @param items array of items that will be the data for the recycler view.
   */
  @MainThread
  @SuppressWarnings("unused")
  public void setItems(@NonNull T[] items) {
    setItems(Arrays.asList(items));
  }

  /**
   * Adds items to the recycler view.
   *
   * @param items the items that will be the recycler view data.
   * @return if item was successfully added
   */
  @MainThread
  @SuppressWarnings("unused")
  public boolean addItems(@Nullable List<? extends T> items) {
    return addItems(this, items);
  }

  /**
   * Adds an item to the recycler's view data and notifies any registered observers that an item has been added.
   *
   * @param item The item to be added.
   */
  public void addItem(@NonNull Object item) {
    addItem(this, item);
  }

  /**
   * Adds a list of items to the recycler's view data and notifies any registered observers that the items has been
   * added.
   *
   * @param index The index at which the specified items are to be inserted.
   * @param items The items that will be the data for the recycler view.
   * @return if items was successfully added.
   */
  public boolean addItems(int index, @Nullable List<?> items) {
    return addItems(index, this, items);
  }

  /**
   * Sets the items for the recycler view and notifying any registered observers that the data set has
   * changed. It uses a function that calculates the difference between the old and the new items
   * in order to improve the update process.
   *
   * @param newItems                  The items tobe added.
   * @param areItemsTheSameFunction   A function which checks that two items are the same.
   * @param areContentTheSameFunction A function which checks that the content of two items are the same.
   */
  protected void setItems(final @Nullable List<T> newItems,
                          @NonNull BiFunction<T, T, Boolean> areItemsTheSameFunction,
                          @NonNull BiFunction<T, T, Boolean> areContentTheSameFunction) {
    setItems(this, newItems, areItemsTheSameFunction, areContentTheSameFunction);
  }

  @Override
  public void onBindViewHolder(@NonNull VH viewHolder, @NonNull T item, int position) {

  }
}
