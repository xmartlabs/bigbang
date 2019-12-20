package com.xmartlabs.bigbang.ui.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xmartlabs.bigbang.core.extensions.orDo
import java.util.*

/**
 * [RecyclerView.ItemDecoration] subclass designed to add spacing to item controlled by a [GridLayoutManager].
 *
 * This decorator relies on both the [GridLayoutManager.mSpanCount] set and the
 * [GridLayoutManager.SpanSizeLookup], so both must be defined.
 *
 * This decorator allows setting spacing for every item, and different spacing for:
 *
 *  * First row items
 *  * Last row items
 *  * First column items
 *  * Last column items
 *
 * There's another option for which you can set the spacing individually for every item, using the
 * [.setItemOffsetConsumer] consumer function.
 *
 * Note that calculating the first and last column for each row involves some processing and can hurt performance.
 * Because of that, those values are calculated once and then cached for faster access.
 * If new items are added to the [RecyclerView], you must invalidate the cache for the decoration to work
 * properly, using one of the following methods:
 *
 *  * [.invalidateCache] to invalidate the whole cache
 *  * [.invalidateCacheFromPosition] to invalidate the cache from a given position (if you append items
 * to the latest position, using this will yield better performance)
 *
 * For even faster performance, consider enabling [GridLayoutManager.SpanSizeLookup.setSpanIndexCacheEnabled].
 */
class RecyclerViewGridSpacingDecoration : RecyclerView.ItemDecoration() {
  /** Top spacing for the first row. If null, [.itemSpacing] will be used.  */
  @Dimension(unit = Dimension.PX)
  var firstRowTopSpacing: Int? = null
  /** Bottom spacing for the last row. If null, [.itemSpacing] will be used.  */
  @Dimension(unit = Dimension.PX)
  var lastRowBottomSpacing: Int? = null
  /** Left spacing for the first column. If null, [.itemSpacing] will be used.  */
  @Dimension(unit = Dimension.PX)
  var firstColumnLeftSpacing: Int? = null
  /** Right spacing for the last column. If null, [.itemSpacing] will be used.  */
  @Dimension(unit = Dimension.PX)
  var lastColumnRightSpacing: Int? = null
  /**
   * Used to manually set the offset for every item.
   * This will override the automatic calculations.
   * The [Rect] top, right, bottom, left parameters must be modified to set the offset.
   */
  var itemOffsetConsumer: ((Rect, RecyclerView) -> Unit)? = null
  /** The default spacing for every item (top, right, bottom, left), unless one of the above spacings apply.  */
  @Dimension(unit = Dimension.PX)
  var itemSpacing: Int = 0

  private val firstColumns = ArrayList<Int>()
  private var biggestFirstColumn: Int = 0
  private val lastColumns = ArrayList<Int>()
  private var biggestLastColumn: Int = 0

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    if (parent.layoutManager !is GridLayoutManager) {
      throw IllegalArgumentException("This Item Decoration can only be used with GridLayoutManager")
    }
    
    val layoutManager = parent.layoutManager as GridLayoutManager
    itemOffsetConsumer?.invoke(outRect, parent) ?: setOffsetForItem(outRect, view, parent, layoutManager)
  }

  /**
   * Sets the offset (spacing) for the `view`.

   * @param outRect the bounds of the view. The spacing must be set to this object
   * *
   * @param view the view to add the spacing
   * *
   * @param recyclerView the recycler view that holds the `view`
   * *
   * @param gridLayoutManager the layout manager of the recycler view
   */
  private fun setOffsetForItem(outRect: Rect, view: View, recyclerView: RecyclerView, gridLayoutManager: GridLayoutManager) {
    val position = recyclerView.getChildLayoutPosition(view)
    val spanCount = gridLayoutManager.spanCount
    val numberOfItems = recyclerView.adapter!!.itemCount
    val spanSizeLookup = gridLayoutManager.spanSizeLookup
  
    itemOffsetConsumer?.invoke(outRect, recyclerView).orDo {
      val firstRowTopSpacing = this.firstRowTopSpacing ?: itemSpacing
      val firstColumnLeftSpacing = this.firstColumnLeftSpacing ?: itemSpacing
      val lastColumnRightSpacing = this.lastColumnRightSpacing ?: itemSpacing
      val lastRowBottomSpacing = this.lastRowBottomSpacing ?: itemSpacing
  
      outRect.top = if (isFirstRow(position, spanCount, spanSizeLookup)) firstRowTopSpacing else itemSpacing / 2
      outRect.left = if (isFirstColumn(position, spanCount, spanSizeLookup)) firstColumnLeftSpacing else itemSpacing / 2
      outRect.right = if (isLastColumn(position, spanCount, numberOfItems, spanSizeLookup)) lastColumnRightSpacing else itemSpacing / 2
      outRect.bottom = if (isLastRow(position, spanCount, numberOfItems, spanSizeLookup)) lastRowBottomSpacing else itemSpacing / 2
    }
  }

  /**
   * Calculates whether or not the item at `position` belongs to the first row.

   * @param position the item position
   * *
   * @param spanCount the maximum number of items a row can hold
   * *
   * @param spanSizeLookup the object that defines how much space an item can take
   * *
   * @return whether or not the item belong to the first row
   */
  private fun isFirstRow(position: Int, spanCount: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup): Boolean {
    return spanSizeLookup.getSpanGroupIndex(position, spanCount) == FIRST_ROW_GROUP
  }

  /**
   * Calculates whether or not the item at `position` belongs to the last row.

   * @param position the item position
   * *
   * @param spanCount the maximum number of items a row can hold
   * *
   * @param numberOfItems the total number of items held by the [RecyclerView]
   * *
   * @param spanSizeLookup the object that defines how much space an item can take
   * *
   * @return whether or not the item belongs to the last row
   */
  private fun isLastRow(position: Int, initialSpanCount: Int, numberOfItems: Int,
                        spanSizeLookup: GridLayoutManager.SpanSizeLookup): Boolean {
    var spanCount = initialSpanCount
    var items = numberOfItems
    while (position < numberOfItems - 1) {
      val spanSize = spanSizeLookup.getSpanSize(numberOfItems - 1)
      spanCount -= spanSize
      items -= 1
      if (spanCount < 0) {
        return false
      }
    }
    return true
  }

  /**
   * Returns whether the item at `position` belongs to the first column.

   * @param position the item position
   * *
   * @param spanCount the maximum number of items a row can hold
   * *
   * @param spanSizeLookup the object that defines how much space an item can take
   * *
   * @return whether or not the item belongs to the first column
   */
  private fun isFirstColumn(position: Int, spanCount: Int, spanSizeLookup: GridLayoutManager.SpanSizeLookup): Boolean {
    if (position == 0 || firstColumns.contains(position)) {
      biggestFirstColumn = if (biggestFirstColumn < position) position else biggestFirstColumn
      return true
    }
    if (position < biggestFirstColumn) {
      return false
    }

    val isFirstColumn = spanSizeLookup.getSpanGroupIndex(position, spanCount) > spanSizeLookup.getSpanGroupIndex(position - 1, spanCount)
    if (isFirstColumn) {
      biggestFirstColumn = if (biggestFirstColumn < position) position else biggestFirstColumn
    }
    return isFirstColumn
  }

  /**
   * Returns whether the item at `position` belongs to the last column.

   * @param position the item position
   * *
   * @param spanCount the maximum number of items a row can hold
   * *
   * @param numberOfItems the total number of items held by the [RecyclerView]
   * *
   * @param spanSizeLookup the object that defines how much space an item can take
   * *
   * @return whether or not the item belongs to the last column
   */
  private fun isLastColumn(position: Int, spanCount: Int, numberOfItems: Int,
                           spanSizeLookup: GridLayoutManager.SpanSizeLookup): Boolean {
    if (position == numberOfItems - 1 || lastColumns.contains(position)) {
      biggestLastColumn = if (biggestLastColumn < position) position else biggestLastColumn
      return true
    }
    if (position < biggestLastColumn) {
      return false
    }

    val isLastColumn = spanSizeLookup.getSpanGroupIndex(position, spanCount) < spanSizeLookup.getSpanGroupIndex(position + 1, spanCount)
    if (isLastColumn) {
      biggestLastColumn = if (biggestLastColumn < position) position else biggestLastColumn
    }
    return isLastColumn
  }

  /**
   * Invalidates the cache holding the information about which items belong to the first or last column.

   * If [GridLayoutManager.SpanSizeLookup.setSpanIndexCacheEnabled] is enabled and the recycler view
   * adapter did not suffer any change, then you must invalidate the [GridLayoutManager.SpanSizeLookup] cache
   * calling [GridLayoutManager.SpanSizeLookup.invalidateSpanIndexCache].
   */
  fun invalidateCache() {
    firstColumns.clear()
    biggestFirstColumn = 0
    lastColumns.clear()
    biggestLastColumn = 0
  }

  /**
   * Invalidates the cache holding the information about which items belong to the first or last column from the
   * specified `position`.

   * If [GridLayoutManager.SpanSizeLookup.setSpanIndexCacheEnabled] is enabled and the recycler view
   * adapter did not suffer any change, then you must invalidate the [GridLayoutManager.SpanSizeLookup] cache
   * calling [GridLayoutManager.SpanSizeLookup.invalidateSpanIndexCache].

   * @param position the position from which the cache should be invalidated
   */
  fun invalidateCacheFromPosition(position: Int) {
    firstColumns.removeAll(firstColumns.filter { it <= position })
    biggestFirstColumn = firstColumns[firstColumns.size - 1]
    lastColumns.removeAll(lastColumns.filter { it <= position })
    biggestLastColumn = lastColumns[lastColumns.size - 1]
  }

  companion object {
    private val FIRST_ROW_GROUP = 0
  }
}
