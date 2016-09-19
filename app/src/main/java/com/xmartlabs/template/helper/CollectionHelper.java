package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Comparator;

/**
 * Created by medina on 19/09/2016.
 */
public class CollectionHelper {
  public static boolean isNullOrEmpty(@Nullable Collection collection) {
    return collection == null || collection.isEmpty();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings("unused")
  public static <T extends Comparable<T>> Comparator<T> getSortedDescComparator() {
    return (v1, v2) -> v2.compareTo(v1);
  }
}
