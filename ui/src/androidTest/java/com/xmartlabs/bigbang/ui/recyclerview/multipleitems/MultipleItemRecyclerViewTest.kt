package com.xmartlabs.bigbang.ui.recyclerview.multipleitems

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.xmartlabs.bigbang.test.assertions.RecyclerViewAssertions
import com.xmartlabs.bigbang.test.extensions.checkRecyclerViewAtPosition
import com.xmartlabs.bigbang.test.extensions.checkRecyclerViewCountIs
import com.xmartlabs.bigbang.ui.recyclerview.common.Brand
import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import com.xmartlabs.bigbang.ui.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MultipleItemRecyclerViewTest {
  @Rule
  @JvmField var activityRule = ActivityTestRule(MultipleItemActivity::class.java)

  @Test
  fun testSetItems() {
    val brands = brands

    val activity = activityRule.activity
    activity.runOnUiThread { activity.setItems(brands) }

    val recyclerViewInteraction = onView(withId(R.id.recycler_view))
    recyclerViewInteraction.check(matches(RecyclerViewAssertions.countIs(6)))

    recyclerViewInteraction.checkRecyclerViewCountIs(6)

    brands
        .flatMap { mutableListOf(it.name).apply { addAll(it.cars.map(Car::model)) } }
        .forEachIndexed { index, brand ->
          recyclerViewInteraction.checkRecyclerViewAtPosition(index, withText(brand))
        }
  }

  private val brands: List<Brand>
    get() {
      val corsa = Car("Corsa")
      val gol = Car("Gol")
      val golf = Car("Golf")
      val saveiro = Car("Saveiro")
      val chevrolet = Brand("Chevrolet", listOf(corsa))
      val volkswagen = Brand("Volkswagen", listOf(gol, golf, saveiro))
      return listOf(chevrolet, volkswagen)
    }
}
