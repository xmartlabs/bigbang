package com.xmartlabs.base.core.helper.ui;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

@SuppressWarnings("unused")
public class ColorHelper {
  /**
   * Retrieves the Hex string representation of the color resource.
   *
   * @param colorResId the id of the color resource
   * @return the Hex string representation of the color resource
   */
  @NonNull
  public static String getResourceColorHexString(@NonNull Context context, @ColorRes int colorResId) {
    //noinspection deprecation
    int color = context.getResources().getColor(colorResId);
    return String.format("#%06X", (0xFFFFFF & color));
  }
}
