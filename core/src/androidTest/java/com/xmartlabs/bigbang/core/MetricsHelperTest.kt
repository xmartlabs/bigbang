package com.xmartlabs.bigbang.core

import android.content.res.Resources
import android.support.annotation.DimenRes
import android.support.annotation.Dimension
import android.support.test.runner.AndroidJUnit4
import android.util.DisplayMetrics
import com.xmartlabs.bigbang.core.extensions.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

@RunWith(AndroidJUnit4::class)
class MetricsHelperTest {
  companion object {
    private val DENSITY = 2.5f
  }
  
  @Rule
  @JvmField var mockitoRule = MockitoJUnit.rule()
  
  @Mock
  internal lateinit var resources: Resources
  @Mock
  internal lateinit var displayMetrics: DisplayMetrics

  @Before
  fun setUp() {
    displayMetrics.density = DENSITY
    displayMetrics.scaledDensity = DENSITY
    Mockito.`when`(resources.displayMetrics).thenReturn(displayMetrics)
  }

  @Test
  fun testDpToPx() {
    @Dimension(unit = Dimension.DP) val dp = 10f
    Assert.assertEquals(25.0f, resources.metric.dpToPx(dp))
  }

  @Test
  fun testSpToPx() {
    @Dimension(unit = Dimension.SP) val sp = 10f
    Assert.assertEquals(25.0f, resources.metric.spToPx(sp))
  }

  @Test
  fun testDpToPxInt() {
    @Dimension(unit = Dimension.DP) val dp = 3f
    Assert.assertEquals(7, resources.metric.dpToPxInt(dp))
  }

  @Test
  fun testDimResToPxInt() {
    @DimenRes val res = 14
    val returned = 12.5f
    Mockito.`when`(resources.getDimension(res)).thenReturn(returned)

    Assert.assertEquals(12, resources.metric.dimResToPxInt(res))
  }

  @Test
  fun testPxToDp() {
    @Dimension(unit = Dimension.PX) val px = 3.5f
    Assert.assertEquals(1.4f, resources.metric.pxToDp(px))
  }
}
