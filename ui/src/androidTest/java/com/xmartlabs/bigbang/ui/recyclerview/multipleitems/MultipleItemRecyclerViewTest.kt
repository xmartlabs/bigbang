package com.xmartlabs.bigbang.ui.recyclerview.multipleitems

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.xmartlabs.bigbang.ui.recyclerview.common.Brand
import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import com.xmartlabs.bigbang.ui.recyclerview.common.RecyclerViewAssertions
import com.xmartlabs.bigbang.ui.test.R
import org.hamcrest.Matcher
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

    checkRecyclerViewCondition(RecyclerViewAssertions.countIs(6))
    
    brands
        .flatMap { mutableListOf(it.name).apply { addAll(it.cars.map(Car::model)) } }
        .forEachIndexed { index, brand ->
          checkRecyclerViewCondition(
              RecyclerViewAssertions.atPosition(index, withText(brand)))
        }
  }

  private fun checkRecyclerViewCondition(viewMatcher: Matcher<View>) {
    val recyclerViewInteraction = onView(withId(R.id.recycler_view))
    recyclerViewInteraction.check(matches(viewMatcher))
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
