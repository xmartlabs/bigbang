package com.xmartlabs.template.helper.ui;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;

/**
 * Created by medina on 19/09/2016.
 */
public class ColorHelper {
  @NonNull
  public static String getResourceColorHexString(@ColorRes int colorResId) {
    //noinspection deprecation
    int color = BaseProjectApplication.getContext().getResources().getColor(colorResId);
    return String.format("#%06X", (0xFFFFFF & color));
  }
}
