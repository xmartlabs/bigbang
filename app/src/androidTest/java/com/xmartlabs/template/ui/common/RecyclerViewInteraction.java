package com.xmartlabs.template.ui.common;

import android.support.test.espresso.NoMatchingViewException;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;

/**
 * Created by medina on 21/09/2016.
 * Copied from https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e#.dm9yzv52b.
 */
public class RecyclerViewInteraction<A> {
  private Matcher<View> viewMatcher;
  private List<A> items;

  private RecyclerViewInteraction(Matcher<View> viewMatcher) {
    this.viewMatcher = viewMatcher;
  }

  public static <A> RecyclerViewInteraction<A> onRecyclerView(Matcher<View> viewMatcher) {
    return new RecyclerViewInteraction<>(viewMatcher);
  }

  public RecyclerViewInteraction<A> withItems(List<A> items) {
    this.items = items;
    return this;
  }

  public RecyclerViewInteraction<A> check(ItemViewAssertion<A> itemViewAssertion) {
    for (int i = 0; i < items.size(); i++) {
      onView(viewMatcher)
          .perform(scrollToPosition(i))
          .check(new RecyclerItemViewAssertion<>(i, items.get(i), itemViewAssertion));
    }
    return this;
  }

  public interface ItemViewAssertion<A> {
    void check(A item, View view, NoMatchingViewException e);
  }
}
