package com.xmartlabs.template;

import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import com.xmartlabs.bigbang.core.helper.ui.MetricsHelper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

@RunWith(AndroidJUnit4.class)
public class MetricsHelperTest {
  private static final float DENSITY = 2.5f;

  @Mock
  Resources resources;
  @Mock
  DisplayMetrics displayMetrics;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    displayMetrics.density = DENSITY;
    displayMetrics.scaledDensity = DENSITY;
    Mockito.when(resources.getDisplayMetrics()).thenReturn(displayMetrics);
  }

  @Test
  public void testDpToPx() {
    @Dimension(unit = Dimension.DP) int dp = 10;
    Assert.assertEquals(25.0f, MetricsHelper.dpToPx(resources, dp));
  }

  @Test
  public void testSpToPx() {
    @Dimension(unit = Dimension.SP) int sp = 10;
    Assert.assertEquals(25.0f, MetricsHelper.spToPx(resources, sp));
  }

  @Test
  public void testDpToPxInt() {
    @Dimension(unit = Dimension.DP) int dp = 3;
    Assert.assertEquals(7, MetricsHelper.dpToPxInt(resources, dp));
  }

  @Test
  public void testDimResToPxInt() {
    @DimenRes int res = 14;
    float returned = 12.5f;
    Mockito.when(resources.getDimension(res)).thenReturn(returned);

    Assert.assertEquals(12, MetricsHelper.dimResToPxInt(resources, res));
  }

  @Test
  public void testPxToDp() {
    @Dimension(unit = Dimension.PX) float px = 3.5f;
    Assert.assertEquals(1.4f, MetricsHelper.pxToDp(resources, px));
  }

  @Test
  public void testEuclideanDistance() {
    Assert.assertEquals(5.0f, MetricsHelper.euclideanDistance(4, 2, 0, -1));
  }
}
