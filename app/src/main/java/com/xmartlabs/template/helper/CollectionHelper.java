package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class CollectionHelper {
  /**
   * Checks whether or not a collection is null or empty.
   *
   * @param collection the <code>Collection</code> instance to be checked
   * @return true if the <code>collection</code> is null or empty
   */
  public static boolean isNullOrEmpty(@Nullable Collection collection) {
    return collection == null || collection.isEmpty();
  }

  /**
   * Returns a desc <code>Comparator</code> of any <code>Comparable</code> type.
   *
   * @param <T> the type of the <code>Comparator</code>, which must implement the <code>Comparable</code> interface
   * @return the <code>Comparator</code> instance
   */
  @CheckResult
  @NonNull
  @SuppressWarnings("unused")
  public static <T extends Comparable<T>> Comparator<T> getSortedDescComparator() {
    return (v1, v2) -> v2.compareTo(v1);
  }
}
