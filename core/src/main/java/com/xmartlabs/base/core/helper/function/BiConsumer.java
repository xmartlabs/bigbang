package com.xmartlabs.base.core.helper.function;

import android.support.annotation.NonNull;

/**
 * A functional interface (callback) that accepts two values (of possibly different types).
 *
 * @param <T1> the first value type
 * @param <T2> the second value type
 */
public interface BiConsumer<T1, T2> {
  /**
   * Performs an operation on the given values.
   *
   * @param t1 the first value
   * @param t2 the second value
   */
  void accept(@NonNull T1 t1, @NonNull T2 t2);
}

