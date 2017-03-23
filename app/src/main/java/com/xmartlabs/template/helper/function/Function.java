package com.xmartlabs.template.helper.function;

import android.support.annotation.NonNull;

/**
 * A functional interface that takes a value and returns another value, possibly with a
 * different type and allows throwing a checked exception.
 *
 * @param <T> the input value type
 * @param <R> the output value type
 */
public interface Function<T, R> {
  /**
   * Apply some calculation to the input value and return some other value.
   *
   * @param t the input value
   * @return the output value
   */
  @NonNull
  R apply(@NonNull T t);
}
