package com.xmartlabs.template.ui.common

import android.app.Activity
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.xmartlabs.template.ui.common.RecyclerViewAssertions.countIs
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RequestsVerifier
import io.appflate.restmock.utils.RequestMatcher
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class SingleActivityWithRecyclerViewInstrumentationTest<T : Activity> : SingleActivityInstrumentationTest<T>() {
  protected fun checkListSizeTest(testData: RecyclerViewItemsTestData) {
    var requestMatcher: RequestMatcher = pathContains("/" + testData.serviceUrl)
    RESTMockServer
        .whenGET(requestMatcher)
        .thenReturnFile(testData.jsonResponsePath)
  
    activityTestRule.launchActivity(testData.intent)
  
    testData.content
        .forEach { item -> onView(allOf(isAssignableFrom(RecyclerView::class.java),
            anyOf(withId(item.first), isDescendantOfA(withId(item.first)))))
            .check(matches(countIs(item.second))) }
  
    RequestsVerifier.verifyGET(requestMatcher).invoked()
  }

  @Test
  @Throws(Throwable::class)
  fun hasNoItemsRecyclerView() {
    checkListSizeTest(noItemsRecyclerViewItemsTestData)
  }

  @Test
  fun hasItemCountRecyclerView() {
    checkListSizeTest(itemsRecyclerViewItemsTestData)
  }

  abstract val noItemsRecyclerViewItemsTestData: RecyclerViewItemsTestData

  abstract val itemsRecyclerViewItemsTestData: RecyclerViewItemsTestData
}
