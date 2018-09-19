package com.xmartlabs.bigbang.ui.common.recyclerview;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Exceptional;
import com.annimon.stream.IntPair;
import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.bigbang.core.helper.CollectionHelper;
import com.xmartlabs.bigbang.core.helper.ObjectHelper;
import com.xmartlabs.bigbang.core.helper.function.BiFunction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import timber.log.Timber;

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
  private Disposable updateElementsDisposable;

  @AllArgsConstructor
  @Data
  @SuppressWarnings("WeakerAccess")
  protected static class Element {
    RecycleItemType type;
    Object item;
  }

  @Override
  public final RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //noinspection unchecked
    return types.get(viewType).onCreateViewHolder(parent);
  }

  /**
   * Removes an item from the data and any registered observers of its removal.
   *
   * @param item the item to be removed.
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

  /**
   * Removes a list of items from the data and any registered observers of its removal.
   *
   * @param items the list of items to be removed.
   */
  @MainThread
  public void removeItems(@NonNull List<Object> items) {
    Stream.of(items)
        .distinct()
        .forEach(this::removeItem);
  }

  /**
   * Adds an item to the data for the recycler view without notifying any registered observers that an item has been
   * added.
   *
   * @param type            The type of the item.
   * @param item            The item to be added.
   * @param addTypeIfNeeded A boolean specifying if the item type has to be added to the type collection.
   *                        If this parameter is true, the type will be added only if it wasn't added yet.
   */
  protected <T extends RecycleItemType> void addItemWithoutNotifying(@NonNull T type, @NonNull Object item,
                                                                     boolean addTypeIfNeeded) {
    addItemWithoutNotifying(items.size(), type, item, addTypeIfNeeded);
  }

  /**
   * Adds an item to the data for the recycler view without notifying any registered observers that an item has been
   * added.
   *
   * @param index           The index at which the specified items are to be inserted.
   * @param type            The type of the item.
   * @param item            The item to be added.
   * @param addTypeIfNeeded A boolean specifying if the item type has to be added to the type collection.
   *                        If this parameter is true, the type will be added only if it wasn't added yet.
   */
  private <T extends RecycleItemType> void addItemWithoutNotifying(int index, @NonNull T type, @Nullable Object item,
                                                                   boolean addTypeIfNeeded) {
    Element element = new Element(type, item);
    items.add(index, element);
    if (addTypeIfNeeded) {
      addItemTypeIfNeeded(type);
    }
  }

  /**
   * Add the type to the collection type only if it is needed.
   *
   * @param type The type to be added.
   */
  private <T extends RecycleItemType> void addItemTypeIfNeeded(@NonNull T type) {
    if (!types.contains(type)) {
      types.add(type);
    }
  }

  /**
   * Adds an item to the data for the recycler view and notifies any registered observers that an item has been added.
   *
   * @param type The type of the item.
   * @param item The item to be added.
   */
  @MainThread
  protected <T extends RecycleItemType> void addItem(@NonNull T type, @NonNull Object item) {
    addItemWithoutNotifying(type, item, true);
    notifyItemInserted(items.size() - 1);
  }

  /**
   * Adds items to the data for the recycler view and notifies any registered observers that the items has been added.
   *
   * @param index The index at which the specified items are to be inserted.
   * @param type  The type of the item.
   * @param items The items that will be the data for the recycler view.
   * @return if items was successfully added.
   */
  @MainThread
  protected <T extends RecycleItemType> boolean addItems(int index, @NonNull T type, @Nullable List<?> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    Stream.ofNullable(items)
        .indexed()
        .forEach(itemWithPosition ->
            addItemWithoutNotifying(index + itemWithPosition.getFirst(), type, itemWithPosition.getSecond(), false));
    addItemTypeIfNeeded(type);
    notifyItemRangeInserted(index, getItemCount() - lastItemCount);
    return true;
  }

  /**
   * Adds items to the data for the recycler view and notifies any registered observers that the items has been added.
   *
   * @param type  The type of the items.
   * @param items the items that will be the data for the recycler view.
   * @return if item was successfully added.
   */
  @MainThread
  protected <T extends RecycleItemType> boolean addItems(@NonNull T type, @Nullable List<?> items) {
    if (CollectionHelper.isNullOrEmpty(items)) {
      return false;
    }
    int lastItemCount = getItemCount();
    Stream.ofNullable(items)
        .forEach(item -> addItemWithoutNotifying(type, item, false));
    addItemTypeIfNeeded(type);
    if (lastItemCount == 0) {
      notifyDataSetChanged();
    } else {
      notifyItemRangeInserted(lastItemCount, getItemCount() - lastItemCount);
    }
    return true;
  }

  /**
   * Sets the items data for the recycler view and notifying any registered observers that the data set has
   * changed. It uses a function that calculates the difference between the old and the new items
   * in order to improve the update process.
   *
   * @param type                      Type of items.
   * @param newItems                  The items tobe added.
   * @param areItemsTheSameFunction   A function which checks that two items are the same.
   * @param areContentTheSameFunction A function which checks that the content of two items are the same.
   */
  @SuppressWarnings("WeakerAccess")
  protected <T, R extends RecycleItemType> void setItems(@NonNull R type, final @Nullable List<T> newItems,
                                                         @NonNull BiFunction<T, T, Boolean> areItemsTheSameFunction,
                                                         @NonNull BiFunction<T, T, Boolean> areContentTheSameFunction) {
    if (CollectionHelper.isNullOrEmpty(newItems)) {
      clearAll();
      return;
    }

    if (updateElementsDisposable != null && !updateElementsDisposable.isDisposed()) {
      updateElementsDisposable.dispose();
    }
    updateElementsDisposable = Single.fromCallable(() -> DiffUtil
        .calculateDiff(getUpdateDiffCallback(newItems, areItemsTheSameFunction, areContentTheSameFunction)))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(diffResult -> {
          items.clear();
          Stream.of(newItems)
              .forEach(item -> addItemWithoutNotifying(type, item, false));
          addItemTypeIfNeeded(type);
          diffResult.dispatchUpdatesTo(this);
        });
  }

  /**
   * Sets the items data for the recycler view and notifying any registered observers that the data set has
   * changed. It uses a function that calculates the difference between the old and the new items
   * in order to improve the update process.
   *
   * @param <T>                       Type of the items to be added.
   * @param newItems                  Items to be added. Each Pair consists of an item and its RecycleItemType.
   * @param areItemsTheSameFunction   A function which checks that two items are the same.
   * @param areContentTheSameFunction A function which checks that the content of two items are the same.
   */
  protected <T> void setMultipleTypeItems(final @Nullable List<Pair<? extends RecycleItemType,T>> newItems,
                                          @NonNull BiFunction<T, T, Boolean> areItemsTheSameFunction,
                                          @NonNull BiFunction<T, T, Boolean> areContentTheSameFunction) {
    if (CollectionHelper.isNullOrEmpty(newItems)) {
      clearAll();
      return;
    }

    if (updateElementsDisposable != null && !updateElementsDisposable.isDisposed()) {
      updateElementsDisposable.dispose();
    }
    List<T> newItemsContent = Stream.ofNullable(newItems)
        .map(pair -> pair.second)
        .toList();

    updateElementsDisposable = Single.fromCallable(() -> DiffUtil
        .calculateDiff(getUpdateDiffCallback(newItemsContent, areItemsTheSameFunction, areContentTheSameFunction)))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(diffResult -> {
          items.clear();
          Stream.of(newItems)
              .forEach(pair -> addItemWithoutNotifying(pair.first, pair.second, true));
          diffResult.dispatchUpdatesTo(this);
        });
  }

  @NonNull
  private <T> DiffUtil.Callback getUpdateDiffCallback(
      @NonNull final List<T> newItems,
      @NonNull final BiFunction<T, T, Boolean> areItemsTheSameFunction,
      @NonNull final BiFunction<T, T, Boolean> areContentTheSameFunction) {
    return new DiffUtil.Callback() {
      @Override
      public int getOldListSize() {
        return items.size();
      }

      @Override
      public int getNewListSize() {
        return newItems.size();
      }

      @Override
      public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //noinspection unchecked
        return Exceptional.of(() -> areItemsTheSameFunction.apply(
            (T) items.get(oldItemPosition).getItem(),
            newItems.get(newItemPosition)))
            .ifException(Timber::w)
            .getOrElse(false);
      }

      @Override
      public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //noinspection unchecked
        return Exceptional.of(() -> areContentTheSameFunction.apply(
            (T) items.get(oldItemPosition).getItem(),
            newItems.get(newItemPosition)))
            .ifException(Timber::w)
            .getOrElse(false);
      }
    };
  }

  /**
   * Sets the items data for the recycler view and notifies any registered observers that the data set has
   * changed.
   *
   * @param type     Type of items.
   * @param newItems The items tobe added.
   */
  @MainThread
  @SuppressWarnings("WeakerAccess")
  protected <T extends RecycleItemType> void setItems(@NonNull T type, @Nullable List<?> newItems) {
    items.clear();
    addItems(type, newItems);
  }

  /**
   * Gets all the items count, including dividers.
   *
   * @return number of total items.
   */
  @Override
  public int getItemCount() {
    return items.size();
  }

  /**
   * Inflates the view layout/elements.
   *
   * @param parent      the parent viewgroup.
   * @param layoutResId the layout resource id.
   * @return the inflated view.
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
   * Gets the item type.
   *
   * @param position of the item among all items conforming the recycler view.
   * @return item divider type.
   */
  @Override
  public int getItemViewType(int position) {
    return types.indexOf(items.get(position).getType());
  }

  /** Removes all items and notifies that the data has changed. */
  @MainThread
  public void clearAll() {
    items.clear();
    notifyDataSetChanged();
  }

  /**
   * Removes all items in the recyclerView of a specified type.
   *
   * @param itemType The specified type of items to be removed.
   */
  @MainThread
  public void clearItems(@NonNull RecycleItemType itemType) {
    final List<Object> itemsToRemove = Stream.ofNullable(items)
        .filter(value -> value.getType().equals(itemType))
        .map(Element::getItem)
        .toList();

    removeItems(itemsToRemove);
  }
}
