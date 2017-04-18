package com.xmartlabs.base.ui;

import android.graphics.Rect;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.BiConsumer;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

/**
 * {@link RecyclerView.ItemDecoration} subclass designed to add spacing to item controlled by a {@link GridLayoutManager}.
 *
 * This decorator relies on both the {@link GridLayoutManager#mSpanCount} set and the
 * {@link GridLayoutManager.SpanSizeLookup}, so both must be defined.
 *
 * This decorator allows setting spacing for every item, and different spacing for:
 * <ul>
 *   <li>First row items</li>
 *   <li>Last row items</li>
 *   <li>First column items</li>
 *   <li>Last column items</li>
 * </ul>
 *
 * There's another option for which you can set the spacing individually for every item, using the
 * {@link #setItemOffsetConsumer} consumer function.
 *
 * Note that calculating the first and last column for each row involves some processing and can hurt performance.
 * Because of that, those values are calculated once and then cached for faster access.
 * If new items are added to the {@link RecyclerView}, you must invalidate the cache for the decoration to work
 * properly, using one of the following methods:
 * <ul>
 *   <li>{@link #invalidateCache()} to invalidate the whole cache</li>
 *   <li>{@link #invalidateCacheFromPosition(int)} to invalidate the cache from a given position (if you append items
 *       to the latest position, using this will yield better performance)</li>
 * </ul>
 *
 * For even faster performance, consider enabling {@link GridLayoutManager.SpanSizeLookup#setSpanIndexCacheEnabled(boolean)}.
 */
@Builder
public class RecyclerViewGridSpacingDecoration extends RecyclerView.ItemDecoration {
  private static final int FIRST_ROW_GROUP = 0;

  /** Top spacing for the first row. If null, {@link #itemSpacing} will be used. */
  @Dimension(unit = Dimension.PX)
  @Nullable
  private Integer firstRowTopSpacing;
  /** Bottom spacing for the last row. If null, {@link #itemSpacing} will be used. */
  @Dimension(unit = Dimension.PX)
  @Nullable
  private Integer lastRowBottomSpacing;
  /** Left spacing for the first column. If null, {@link #itemSpacing} will be used. */
  @Dimension(unit = Dimension.PX)
  @Nullable
  private Integer firstColumnLeftSpacing;
  /** Right spacing for the last column. If null, {@link #itemSpacing} will be used. */
  @Dimension(unit = Dimension.PX)
  @Nullable
  private Integer lastColumnRightSpacing;
  /**
   * Used to manually set the offset for every item.
   * This will override the automatic calculations.
   * The {@link Rect} top, right, bottom, left parameters must be modified to set the offset.
   */
  @Nullable
  private BiConsumer<Rect, RecyclerView> setItemOffsetConsumer;
  /** The default spacing for every item (top, right, bottom, left), unless one of the above spacings apply. */
  @Dimension(unit = Dimension.PX)
  private int itemSpacing;

  private final List<Integer> firstColumns = new ArrayList<>();
  private int biggestFirstColumn;
  private final List<Integer> lastColumns = new ArrayList<>();
  private int biggestLastColumn;

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
      throw new IllegalArgumentException("This Item Decoration can only be used with GridLayoutManager");
    }
    GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
    Optional.ofNullable(setItemOffsetConsumer)
        .ifPresentOrElse(
            consumer -> consumer.accept(outRect, parent),
            () -> setOffsetForItem(outRect, view, parent, layoutManager)
        );
  }

  /**
   * Sets the offset (spacing) for the {@code view}.
   *
   * @param outRect the bounds of the view. The spacing must be set to this object
   * @param view the view to add the spacing
   * @param recyclerView the recycler view that holds the {@code view}
   * @param gridLayoutManager the layout manager of the recycler view
   */
  private void setOffsetForItem(Rect outRect, View view, RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
    int position = recyclerView.getChildLayoutPosition(view);
    int spanCount = gridLayoutManager.getSpanCount();
    int numberOfItems = recyclerView.getAdapter().getItemCount();
    GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

    Optional.ofNullable(setItemOffsetConsumer)
        .executeIfPresent(consumer -> consumer.accept(outRect, recyclerView))
        .executeIfAbsent(() -> {
          int firstRowTopSpacing = Optional.ofNullable(this.firstRowTopSpacing).orElse(itemSpacing);
          int firstColumnLeftSpacing = Optional.ofNullable(this.firstColumnLeftSpacing).orElse(itemSpacing);
          int lastColumnRightSpacing = Optional.ofNullable(this.lastColumnRightSpacing).orElse(itemSpacing);
          int lastRowBottomSpacing = Optional.ofNullable(this.lastRowBottomSpacing).orElse(itemSpacing);

          outRect.top = isFirstRow(position, spanCount, spanSizeLookup) ? firstRowTopSpacing : itemSpacing;
          outRect.left = isFirstColumn(position, spanCount, spanSizeLookup) ? firstColumnLeftSpacing : itemSpacing;
          outRect.right = isLastColumn(position, spanCount, numberOfItems, spanSizeLookup) ? lastColumnRightSpacing : itemSpacing;
          outRect.bottom = isLastRow(position, spanCount, numberOfItems, spanSizeLookup) ? lastRowBottomSpacing : itemSpacing;
        });
  }

  /**
   * Calculates whether or not the item at {@code position} belongs to the first row.
   *
   * @param position the item position
   * @param spanCount the maximum number of items a row can hold
   * @param spanSizeLookup the object that defines how much space an item can take
   * @return whether or not the item belong to the first row
   */
  private boolean isFirstRow(int position, int spanCount, @NonNull GridLayoutManager.SpanSizeLookup spanSizeLookup) {
    return spanSizeLookup.getSpanGroupIndex(position, spanCount) == FIRST_ROW_GROUP;
  }

  /**
   * Calculates whether or not the item at {@code position} belongs to the last row.
   *
   * @param position the item position
   * @param spanCount the maximum number of items a row can hold
   * @param numberOfItems the total number of items held by the {@link RecyclerView}
   * @param spanSizeLookup the object that defines how much space an item can take
   * @return whether or not the item belongs to the last row
   */
  private boolean isLastRow(int position, int spanCount, int numberOfItems,
                            @NonNull GridLayoutManager.SpanSizeLookup spanSizeLookup) {
    while (position < numberOfItems - 1) {
      int spanSize = spanSizeLookup.getSpanSize(numberOfItems - 1);
      spanCount -= spanSize;
      numberOfItems -= 1;
      if (spanCount < 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns whether the item at {@code position} belongs to the first column.
   *
   * @param position the item position
   * @param spanCount the maximum number of items a row can hold
   * @param spanSizeLookup the object that defines how much space an item can take
   * @return whether or not the item belongs to the first column
   */
  private boolean isFirstColumn(int position, int spanCount, @NonNull GridLayoutManager.SpanSizeLookup spanSizeLookup) {
    if (position == 0 || firstColumns.contains(position)) {
      biggestFirstColumn = biggestFirstColumn < position ? position : biggestFirstColumn;
      return true;
    }
    if (position < biggestFirstColumn) {
      return false;
    }

    boolean isFirstColumn = spanSizeLookup.getSpanGroupIndex(position, spanCount)
        > spanSizeLookup.getSpanGroupIndex(position - 1, spanCount);
    if (isFirstColumn) {
      biggestFirstColumn = biggestFirstColumn < position ? position : biggestFirstColumn;
    }
    return isFirstColumn;
  }

  /**
   * Returns whether the item at {@code position} belongs to the last column.
   *
   * @param position the item position
   * @param spanCount the maximum number of items a row can hold
   * @param numberOfItems the total number of items held by the {@link RecyclerView}
   * @param spanSizeLookup the object that defines how much space an item can take
   * @return whether or not the item belongs to the last column
   */
  private boolean isLastColumn(int position, int spanCount, int numberOfItems,
                               @NonNull GridLayoutManager.SpanSizeLookup spanSizeLookup) {
    if (position == numberOfItems - 1 || lastColumns.contains(position)) {
      biggestLastColumn = biggestLastColumn < position ? position : biggestLastColumn;
      return true;
    }
    if (position < biggestLastColumn) {
      return false;
    }

    boolean isLastColumn = spanSizeLookup.getSpanGroupIndex(position, spanCount)
        < spanSizeLookup.getSpanGroupIndex(position + 1, spanCount);
    if (isLastColumn) {
      biggestLastColumn = biggestLastColumn < position ? position : biggestLastColumn;
    }
    return isLastColumn;
  }

  /**
   * Invalidates the cache holding the information about which items belong to the first or last column.
   *
   * If {@link GridLayoutManager.SpanSizeLookup#setSpanIndexCacheEnabled(boolean)} is enabled and the recycler view
   * adapter did not suffer any change, then you must invalidate the {@link GridLayoutManager.SpanSizeLookup} cache
   * calling {@link GridLayoutManager.SpanSizeLookup#invalidateSpanIndexCache()}.
   */
  public void invalidateCache() {
    firstColumns.clear();
    biggestFirstColumn = 0;
    lastColumns.clear();
    biggestLastColumn = 0;
  }

  /**
   * Invalidates the cache holding the information about which items belong to the first or last column from the
   * specified {@code position}.
   *
   * If {@link GridLayoutManager.SpanSizeLookup#setSpanIndexCacheEnabled(boolean)} is enabled and the recycler view
   * adapter did not suffer any change, then you must invalidate the {@link GridLayoutManager.SpanSizeLookup} cache
   * calling {@link GridLayoutManager.SpanSizeLookup#invalidateSpanIndexCache()}.
   *
   * @param position the position from which the cache should be invalidated
   */
  public void invalidateCacheFromPosition(int position) {
    firstColumns.removeAll( Stream.of(firstColumns)
        .filter(item -> item <= position)
        .toList());
    biggestFirstColumn = firstColumns.get(firstColumns.size() - 1);
    lastColumns.removeAll(Stream.of(lastColumns)
        .filter(item -> item <= position)
        .toList());
    biggestLastColumn = lastColumns.get(lastColumns.size() -1);
  }
}
