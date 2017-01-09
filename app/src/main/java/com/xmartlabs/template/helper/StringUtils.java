package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by remer on 10/5/15.
 */
@SuppressWarnings("unused")
public class StringUtils {

  /**
   * Checks whether the given string is null or empty.
   *
   * @param string the string to check
   * @return true if <code>string</code> is null or empty
   */
  public static boolean stringIsNullOrEmpty(@Nullable String string) {
    return string == null || string.isEmpty();
  }

  /**
   * Checks whether the given char sequence is null or empty.
   *
   * @param string the char sequence to check
   * @return true if <code>string</code> is null or empty
   */
  public static boolean stringIsNullOrEmpty(@Nullable CharSequence string) {
    return string == null || string.length() == 0;
  }

  /**
   * Changes the first letter of the given word to uppercase.
   *
   * @param word the word to capitalize
   * @return a new <code>string</code> instance with the first letter in uppercase
   */
  @NonNull
  public static String capitalizeWord(@NonNull String word) {
    if (word.isEmpty()) {
      return "";
    } else {
      return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
  }
}
