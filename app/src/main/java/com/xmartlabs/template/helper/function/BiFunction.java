package com.xmartlabs.template.helper.function;

import android.support.annotation.NonNull;

/**
 * A functional interface (callback) that computes a value based on multiple input values.
 * @param <T1> the first value type
 * @param <T2> the second value type
 * @param <R> the result type
 */
public interface BiFunction<T1, T2, R> {

  /**
   * Calculate a value based on the input values.
   * @param t1 the first value
   * @param t2 the second value
   * @return the result value
   * @throws Exception on error
   */
  @NonNull
  R apply(@NonNull T1 t1, @NonNull T2 t2);
}