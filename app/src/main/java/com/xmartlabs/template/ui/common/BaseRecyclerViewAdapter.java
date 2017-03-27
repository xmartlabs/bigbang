package com.xmartlabs.template.ui.common;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmartlabs.template.helper.CollectionHelper;
import com.xmartlabs.template.ui.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A Base Recycler View Adapter with already implemented functions such as
 * Setting, removing, adding, getting items count.
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
  protected static final int ITEM_TYPE_DIVIDER = -1;
  protected static final int ITEM_TYPE_ITEM = -2;

  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private List<? extends T> items = new ArrayList<>();

  /**
   * Used to inflate the view layout/elements.
   *
   * @param parent      the parent viewgroup
   * @param layoutResId the layout resource id
   * @return the inflated view
   */
  protected View inflateView(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
    return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
  }

  /**
   * Used to set the items data for the recycler view and notifying any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be the data for the recycler view
   */
  @MainThread
  public void setItems(@Nullable List<? extends T> items) {
    this.items = items == null ? new ArrayList<>() : items;
    notifyDataSetChanged();
  }

  /**
   * Used to set the items data for the recycler view in form of an array and calls {@link #setItems(List)}
   *
   * @param items array of items that will be the data for the recycler view
   */
  @MainThread
  public void setItems(@NonNull T[] items) {
    setItems(Arrays.asList(items));
  }

  /**
   * Used to remove an item from the data and any registered observers of its removal
   *
   * @param item the item to be removed
   */
  @MainThread
  public void removeItem(@NonNull T item) {
    int position = items.indexOf(item);
    if (position >= 0) {
      items.remove(position);
      notifyDataSetChanged();
    }
  }

  /**
   * Used to add items to the data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param items the items that will be the data for the recycler view
   */
  @MainThread
  public boolean addItems(@Nullable List<? extends T> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    if (lastItemCount == 0) {
      setItems(items);
    } else {
      ArrayList<T> newItems = new ArrayList<>();
      newItems.addAll(this.items);
      newItems.addAll(items);
      this.items = newItems;
      notifyItemRangeInserted(Math.max(0, lastItemCount - 1), getItemCount() - lastItemCount);
    }
    return true;
  }

  /**
   * Used to get all the items count, including dividers
   *
   * @return number of total items
   */
  @Override
  public int getItemCount() {
    int dividerLines = (hasDividers() && getItemCountWithoutDividers() > 0 ? getItemCountWithoutDividers() - 1 : 0);
    if (hasEndDivider() && getItemCountWithoutDividers() > 0) {
      dividerLines++;
    }
    return getItemCountWithoutDividers() + dividerLines;
  }

  /**
   * Used to get only the items count from the data
   *
   * @return number of total items from the data
   */
  protected int getItemCountWithoutDividers() {
    return items.size();
  }

  /**
   * Used to bind the data and divider items with the recycler view
   * Gets each item into the correct view holder for the specified position
   *
   * @param holder   the view holder from the recycler view
   * @param position the position of the item among all the items of the recyclerview
   */
  @CallSuper
  @Override
  public void onBindViewHolder(@NonNull VH holder, int position) {
    T item = isADividerPosition(position) ? null : items.get(getRealItemPosition(position));
    onBindViewHolder(holder, item, position);
    if (!isADividerPosition(position)) {
      onBindItemViewHolder(holder, item);
    }
  }

  /**
   * Used to bind the item with the view holder taking the position into account
   */
  protected void onBindViewHolder(@NonNull VH holder, @Nullable T item, int position) {

  }

  /**
   * Used to bind the item with the view holder
   */
  protected void onBindItemViewHolder(@NonNull VH holder, @Nullable T item) {

  }

  /**
   * Used to get the item position according just to the data items
   *
   * @param position of all the items that compose the recycler view
   */
  protected int getRealItemPosition(int position) {
    return hasDividers() ? position / 2 : position;
  }

  /**
   * Used to get the item type
   *
   * @param position of the item among all items conforming the recycler view
   */
  @Override
  public int getItemViewType(int position) {
    return isADividerPosition(position) ? ITEM_TYPE_DIVIDER : ITEM_TYPE_ITEM;
  }

  /**
   * Used to get if the position in question is a divider
   *
   * @param position of recycler view item
   */
  protected boolean isADividerPosition(int position) {
    return (hasDividers() && position % 2 == 1) || (hasEndDivider() && position == getItemCount());
  }

  /**
   * Used to check if the recycler view has dividers
   */
  protected boolean hasDividers() {
    return false;
  }

  /**
   * Used to check if the recycler view has an end divider
   */
  protected boolean hasEndDivider() {
    return false;
  }
}
