package com.xmartlabs.base.core.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

@SuppressWarnings("unused")
public class StringUtils {
  /**
   * Checks whether the given string is null or empty.
   *
   * @param string the string to check
   * @return true if {@code string} is null or empty
   */
  public static boolean isNullOrEmpty(@Nullable String string) {
    return string == null || string.isEmpty();
  }

  /**
   * Checks whether the given char sequence is null or empty.
   *
   * @param string the char sequence to check
   * @return true if {@code string} is null or empty
   */
  public static boolean isNullOrEmpty(@Nullable CharSequence string) {
    return string == null || string.length() == 0;
  }

  /**
   * Changes the first letter of the given sentence to uppercase.
   *
   * @param text the text to capitalize the first character
   * @return a new {@code string} instance with the first letter in uppercase
   */
  @NonNull
  public static String capitalizeFirstChar(@NonNull String text) {
    return text.isEmpty()
        ? ""
        : text.substring(0, 1).toUpperCase(Locale.getDefault()) + text.substring(1).toLowerCase(Locale.getDefault());
  }
}
