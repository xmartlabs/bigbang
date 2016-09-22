package com.xmartlabs.template.ui.common;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Locale;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;

/**
 * Created by medina on 21/09/2016.
 */
public class RecyclerViewAssertions {
  public static Matcher<View> countIs(int count) {
    return new TypeSafeMatcher<View>() {
      int items = -1;
      @Override
      protected boolean matchesSafely(View view) {
        items = ((RecyclerView) view).getAdapter().getItemCount();
        return items == count;
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(String.format(Locale.US, "has %d item(s) and the list has %d", count, items));
      }
    };
  }

  public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
    checkNotNull(itemMatcher);
    return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
      @Override
      public void describeTo(Description description) {
        description.appendText("has item at position " + position + ": ");
        itemMatcher.describeTo(description);
      }

      @Override
      protected boolean matchesSafely(final RecyclerView view) {
        RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
        return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
      }
    };
  }
}
