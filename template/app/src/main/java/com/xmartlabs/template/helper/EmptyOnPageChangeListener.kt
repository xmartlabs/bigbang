package com.xmartlabs.template.helper

import android.support.v4.view.ViewPager

open class EmptyOnPageChangeListener : ViewPager.OnPageChangeListener {
  override fun onPageScrollStateChanged(state: Int) = Unit
  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
  override fun onPageSelected(position: Int) = Unit
}
