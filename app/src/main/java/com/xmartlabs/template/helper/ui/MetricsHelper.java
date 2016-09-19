package com.xmartlabs.template.helper.ui;

import android.content.res.TypedArray;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.R;

/**
 * Created by medina on 16/09/2016.
 */
public class MetricsHelper {
  @Dimension(unit = Dimension.PX)
  public static float dpToPx(@Dimension(unit = Dimension.DP) float dp) {
    return dp * getContext().getResources().getDisplayMetrics().density;
  }

  @Dimension(unit = Dimension.PX)
  public static float spToPx(@Dimension(unit = Dimension.SP) float sp) {
    return sp * getContext().getResources().getDisplayMetrics().scaledDensity;
  }

  @Dimension(unit = Dimension.PX)
  public static int dpToPxInt(@Dimension(unit = Dimension.DP) float dp) {
    return (int) dpToPx(dp);
  }

  @Dimension(unit = Dimension.DP)
  public static int dimResToPxInt(@DimenRes int dimenId) {
    return dpToPxInt(getContext().getResources().getDimension(dimenId));
  }

  @Dimension(unit = Dimension.PX)
  public static float pxToDp(@Dimension(unit = Dimension.PX) float px) {
    return px / getContext().getResources().getDisplayMetrics().density;
  }

  public static int getToolbarHeight() {
    final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[] {R.attr.actionBarSize});
    int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
    styledAttributes.recycle();
    return toolbarHeight;
  }

  public static float euclideanDistance(float x1, float y1, float x2, float y2) {
    float dx = x1 - x2;
    float dy = y1 - y2;
    return (float) Math.sqrt(dx * dx + dy * dy);
  }

  private static BaseProjectApplication getContext() {
    return BaseProjectApplication.getContext();
  }
}