package com.xmartlabs.base.core.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;

@SuppressWarnings("unused")
public class CollectionHelper {
  /**
   * Checks whether or not a collection is null or empty.
   *
   * @param collection the {@link Collection} instance to be checked
   * @return true if the {@link Collection} is null or empty
   */
  public static boolean isNullOrEmpty(@Nullable Collection collection) {
    return collection == null || collection.isEmpty();
  }

  /**
   * Returns a desc {@link Comparator} of any {@link Comparable} type.
   *
   * @param <T> the type of the {@link Comparator}, which must implement the {@link Comparable} interface
   * @return the {@link Comparator} instance
   */
  @CheckResult
  @NonNull
  @SuppressWarnings("unused")
  public static <T extends Comparable<T>> Comparator<T> getSortedDescComparator() {
    return (v1, v2) -> v2.compareTo(v1);
  }
}
