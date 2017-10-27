package com.xmartlabs.bigbang.test.assertions

import android.support.test.espresso.core.deps.guava.base.Preconditions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.*

object RecyclerViewAssertions {
  fun countIs(count: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
      internal var items = -1
      override fun matchesSafely(view: View): Boolean {
        items = (view as RecyclerView).adapter.itemCount
        return items == count
      }

      override fun describeTo(description: Description) {
        description.appendText(String.format(Locale.US, "has %d item(s) and the list has %d", count, items))
      }
    }
  }

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
