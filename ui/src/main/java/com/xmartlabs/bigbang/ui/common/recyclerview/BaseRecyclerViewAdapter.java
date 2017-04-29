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

import com.annimon.stream.IntPair;
import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.core.helper.CollectionHelper;
import com.xmartlabs.bigbang.core.helper.ObjectHelper;

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
  @Getter(AccessLevel.PROTECTED)
  @NonNull
  private List<Element> items = new ArrayList<>();
  @NonNull
  private final List<RecycleItemType> types = new ArrayList<>();

  @AllArgsConstructor
  @Data
  @SuppressWarnings("WeakerAccess")
  protected static class Element {
    RecycleItemType type;
    Object item;
  }

  @NonNull
  @SuppressWarnings("WeakerAccess")
  protected RecyclerView.ViewHolder onCreateDividerViewHolder(@NonNull ViewGroup parent) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //noinspection unchecked
    return types.get(viewType).onCreateViewHolder(parent);
  }

  @MainThread
  protected <T extends RecycleItemType> void addItem(@NonNull T type, @NonNull Object item) {
    addItemWithoutNotify(type, item);
    notifyItemInserted(items.size() - 1);
  }

  /**
   * Removes an item from the data and any registered observers of its removal
   *
   * @param item the item to be removed
   */
  @MainThread
  public void removeItem(@NonNull Object item) {
    Stream.of(new ArrayList<>(items))
        .indexed()
        .filter(element -> Objects.equals(item, element.getSecond().getItem()))
        .map(IntPair::getFirst)
        .sorted((index1, index2) -> ObjectHelper.compare(index2, index1))
        .forEach(index -> {
          items.remove((int) index);
          notifyItemRemoved(index);
        });
  }

  @MainThread
  public void removeItems(@NonNull List<Object> items) {
    Stream.of(items)
        .distinct()
        .forEach(this::removeItem);
  }

  protected <T extends RecycleItemType> void addItemWithoutNotify(@NonNull T type, @NonNull Object item) {
    addItemWithoutNotify(items.size(), type, item);
  }

  protected <T extends RecycleItemType> void addItemWithoutNotify(int index, @NonNull T type, @Nullable Object item) {
    addItemWithoutNotify(items.size(), type, item, true);
  }

  private <T extends RecycleItemType> void addItemWithoutNotify(int index, @NonNull T type, @Nullable Object item,
                                                                boolean addTypeIfNeeded) {
    Element element = new Element(type, item);
    items.add(index, element);
    if (addTypeIfNeeded) {
      addTypeIfNeeded(type);
    }
  }

  private <T extends RecycleItemType> void addTypeIfNeeded(@NonNull T type) {
    if (!types.contains(type)) {
      types.add(type);
    }
  }

  @MainThread
  protected <T extends RecycleItemType> boolean addItems(int index, @NonNull T type, @Nullable List<?> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    Stream.ofNullable(items)
        .indexed()
        .forEach(itemWithPosition ->
            addItemWithoutNotify(index + itemWithPosition.getFirst(), type, itemWithPosition.getSecond(), false));
    addTypeIfNeeded(type);
    notifyItemRangeInserted(lastItemCount, getItemCount() - lastItemCount);
    return true;
  }

  @MainThread
  protected <T extends RecycleItemType> boolean addItems(@NonNull T type, @Nullable List<?> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    Stream.ofNullable(items)
        .map(item -> new Element(type, item))
        .forEach(element -> BaseRecyclerViewAdapter.this.items.add(element));
    addTypeIfNeeded(type);
    if (lastItemCount == 0) {
      notifyDataSetChanged();
    } else {
      notifyItemRangeInserted(lastItemCount, getItemCount() - lastItemCount);
    }
    return true;
  }

  @MainThread
  @SuppressWarnings("WeakerAccess")
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

  @CallSuper
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    Element element = items.get(position);
    Object item = element.getItem();
    //noinspection unchecked
    element.getType().onBindViewHolder(viewHolder, item, position);
    if (viewHolder instanceof BindingItemViewHolder) {
      //noinspection unchecked
      ((BindingItemViewHolder) viewHolder).bindItem(item);
    }
  }

  /**
   * Gets the item type
   *
   * @param position of the item among all items conforming the recycler view
   * @return item divider type
   */
  @Override
  public int getItemViewType(int position) {
    return types.indexOf(items.get(position).getType());
  }
}
