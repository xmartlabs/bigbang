package com.xmartlabs.bigbang.core

import android.content.res.Resources
import android.support.annotation.DimenRes
import android.support.annotation.Dimension
import android.support.test.runner.AndroidJUnit4
import android.util.DisplayMetrics
import com.xmartlabs.bigbang.core.extensions.displayMetrics
import com.xmartlabs.bigbang.core.extensions.dpToPx
import com.xmartlabs.bigbang.core.extensions.pxToDp
import com.xmartlabs.bigbang.core.extensions.spToPx
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

@RunWith(AndroidJUnit4::class)
class MetricsExtensionsTest {
  companion object {
    private val DENSITY = 2.5f
  }

  @Rule
  @JvmField
  var mockitoRule = MockitoJUnit.rule()

  @Mock
  internal lateinit var resources: Resources
  @Mock
  internal lateinit var displayMetrics23: DisplayMetrics

  @Before
  fun setUp() {
    displayMetrics23.density = DENSITY
    displayMetrics23.scaledDensity = DENSITY

    Mockito.`when`(resources.displayMetrics).thenReturn(displayMetrics23)
    displayMetrics = displayMetrics23
  }

  @Test
  fun testDpToPx() {
    @Dimension(unit = Dimension.DP) val dp = 10
    Assert.assertEquals(25, dp.dpToPx)
  }

  @Test
  fun testSpToPx() {
    @Dimension(unit = Dimension.SP) val sp = 10
    Assert.assertEquals(25, sp.spToPx)
  }

  @Test
  fun testDimResToPxInt() {
    @DimenRes val res = 14
    val returned = 12.5f
    Mockito.`when`(resources.getDimension(res)).thenReturn(returned)

    Assert.assertEquals(12, resources.getDimension(res).toInt())
  }

  @Test
  fun testPxToDp() {
    @Dimension(unit = Dimension.PX) val px = 3.5f
    Assert.assertEquals(1.4f, px.pxToDp)
  }
}
