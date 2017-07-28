package com.xmartlabs.template.ui.common

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matcher

/**
 * Copied from https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e#.dm9yzv52b.
 */
class RecyclerViewInteraction<A> private constructor(private val viewMatcher: Matcher<View>) {
  companion object {
    fun <A> onRecyclerView(viewMatcher: Matcher<View>) = RecyclerViewInteraction<A>(viewMatcher)
  }
  
  private var items: List<A>? = null

  fun withItems(items: List<A>): RecyclerViewInteraction<A> {
    this.items = items
    return this
  }

  fun check(itemViewAssertion: ItemViewAssertion<A>): RecyclerViewInteraction<A> {
    for (i in items!!.indices) {
      onView(viewMatcher)
          .perform(scrollToPosition<RecyclerView.ViewHolder>(i))
          .check(RecyclerItemViewAssertion(i, items!![i], itemViewAssertion))
    }
    return this
  }

  interface ItemViewAssertion<A> {
    fun check(item: A, view: View, e: NoMatchingViewException)
  }
}
