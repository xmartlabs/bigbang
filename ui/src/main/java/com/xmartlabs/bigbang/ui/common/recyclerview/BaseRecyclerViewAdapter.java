package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.core.helper.CollectionHelper;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A Base RecyclerViewAdapter with already implemented functions such as
 * Setting, removing, adding items, getting its count among others.
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int ITEM_TYPE_DIVIDER = -1;

  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private List<Element> items = new ArrayList<>();
  private List<RecycleItemType> types = new ArrayList<>();

  @AllArgsConstructor
  @Data
  @SuppressWarnings("WeakerAccess")
  protected static class Element {
    RecycleItemType type;
    Object item;
  }

  public RecyclerView.ViewHolder onCreateDividerViewHolder(@NonNull ViewGroup parent) {
    return null;
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //noinspection unchecked
    return viewType == ITEM_TYPE_DIVIDER
        ? onCreateDividerViewHolder(parent)
        : types.get(viewType).onCreateViewHolder(parent);
  }

  @MainThread
  protected <T extends RecycleItemType> void addItem(@NonNull T type, @NonNull Object item) {
    addItemWithoutNotifyChanged(type, item);
    notifyItemInserted(items.size() - 1);
  }

  /**
   * Removes an item from the data and any registered observers of its removal
   *
   * @param item the item to be removed
   */
  @MainThread
  protected void removeItem(@NonNull Object item) {
    Stream.of(items)
        .indexed()
        .filter(element -> Objects.equals(item, element.getSecond().getItem()))
        .findFirst()
        .ifPresent(elementIntPair -> {
          int index = elementIntPair.getFirst();
          items.remove(index);
          notifyItemRemoved((hasDividers() ? index * 2 : index) + (hasStartDivider() ? 0 : -1)); // todo check
        });
  }

  protected <T extends RecycleItemType> void addItemWithoutNotifyChanged(@NonNull T type, @NonNull Object item) {
    Element element = new Element(type, item);
    items.add(element);
    addTypeIfNeeded(type);
  }

  private <T extends RecycleItemType> void addTypeIfNeeded(@NonNull T type) {
    if (!types.contains(type)) {
      types.add(type);
    }
  }

  @MainThread
  protected <T extends RecycleItemType> boolean addItems(@NonNull T type, @Nullable List<?> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    Stream.ofNullable(items)
        .map(item -> new Element(type, item))
        .forEach(BaseRecyclerViewAdapter.this.items::add);
    addTypeIfNeeded(type);
    if (lastItemCount == 0) {
      notifyDataSetChanged();
    } else {
      notifyItemRangeInserted(Math.max(0, lastItemCount - 1), getItemCount() - lastItemCount);
    }
    return true;
  }

  @MainThread
  protected <T extends RecycleItemType> void setItems(@NonNull T type, @Nullable List<?> items) {
    addItems(type, items);
  }

  /**
   * Gets all the items count, including dividers
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
   * Gets only the items count from the data
   *
   * @return number of total items from the data
   */
  protected int getItemCountWithoutDividers() {
    return items.size();
  }

  /**
   * Inflates the view layout/elements.
   *
   * @param parent      the parent viewgroup
   * @param layoutResId the layout resource id
   * @return the inflated view
   */
  protected static View inflateView(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    return layoutInflater.inflate(layoutResId, parent, false);
  }

  /** Binds the divider with the view holder */
  public void onBindDividerViewHolder(RecyclerView.ViewHolder holder, int position) {

  }

  @CallSuper
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isADividerPosition(position)) {
      onBindDividerViewHolder(holder, position);
    } else {
      Element element = items.get(getRealItemPosition(position));
      //noinspection unchecked
      element.getType().getOnBindViewHolder(holder, element.getItem(), position);
    }
  }

  /**
   * Gets the item position according just to the data items
   *
   * @param position of all the items that compose the recycler view
   * @return item position within data items
   */
  protected int getRealItemPosition(int position) {
    return hasDividers() ? position / 2 : position;
  }

  /**
   * Gets the item type
   *
   * @param position of the item among all items conforming the recycler view
   * @return item divider type
   */
  @Override
  public int getItemViewType(int position) {
    return isADividerPosition(position)
        ? ITEM_TYPE_DIVIDER
        : types.indexOf(items.get(getRealItemPosition(position)).getType());
  }

  /**
   * @param position of recycler view item
   * @return if the position in question is a divider
   */
  protected boolean isADividerPosition(int position) {
    return (hasStartDivider() && position % 2 == 0)
        || (hasDividers() && position % 2 == 1)
        || (hasEndDivider() && position == getItemCount() - 1);
  }

  /** @return if recycler view has dividers */
  protected boolean hasDividers() {
    return false;
  }

  /** @return if the recycler view has an end divider */
  protected boolean hasEndDivider() {
    return false;
  }

  /** @return if the recycler view has a top divider */
  protected boolean hasStartDivider() {
    return false;
  }
}
