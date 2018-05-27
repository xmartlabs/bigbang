package com.xmartlabs.bigbang.mvp.recyclerview.singleitem

import org.junit.Test

class SimpleItemTestSetItems : SimpleItemRecyclerViewTest() {
  @Test
  fun testSetItems() {
    val cars = carList

    val activity = activityRule.activity
    activity.runOnUiThread { activity.adapter.setItems(cars) }

    checkItems(cars)
  }
}
