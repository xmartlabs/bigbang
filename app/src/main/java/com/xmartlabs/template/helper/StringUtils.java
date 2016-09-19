package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by remer on 10/5/15.
 */
public class StringUtils {
  public static boolean stringIsNullOrEmpty(@Nullable String string) {
    return string == null || string.isEmpty();
  }

  public static boolean stringIsNullOrEmpty(@Nullable CharSequence string) {
    return string == null || string.length() == 0;
  }

  @NonNull
  public static String capitalizeWord(@NonNull String string) {
    if (string.isEmpty()) {
      return "";
    } else {
      return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
  }
}
