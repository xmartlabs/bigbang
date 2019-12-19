package com.xmartlabs.bigbang.test.assertions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * A set of utility functions over RecyclerView views
 */
object RecyclerViewAssertions {
  /**
   * Matches that the recycler view has `count` items
   *
   * @param count the number of items to match against the RecyclerView
   *
   * @return the [Matcher] that verifies that the number of items in the RecyclerView matches `count`
   */
  fun countIs(count: Int): Matcher<View> = object : TypeSafeMatcher<View>() {
    internal var items = -1
    
    override fun matchesSafely(view: View): Boolean {
      items = (view as RecyclerView).adapter.itemCount
      return items == count
    }
  
    override fun describeTo(description: Description) {
      description.appendText("has $count item(s) and the list has $items")
    }
  }
  
  /**
   * Matches that the item given by `itemMatcher` is found at the `position` position
   *
   * @param position the position of the view in the RecyclerView
   * @param itemMatcher the matcher to find the view in the RecyclerView
   *
   * @return the [Matcher] that asserts whether the view at `position` is the same as the matched with `itemMatcher`
   */
  fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    Preconditions.checkNotNull(itemMatcher)
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
      override fun describeTo(description: Description) {
        description.appendText("has item at position $position: ")
        itemMatcher.describeTo(description)
      }

      override fun matchesSafely(view: RecyclerView): Boolean {
        val viewHolder = view.findViewHolderForAdapterPosition(position)
        return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
      }
    }
  }
}
