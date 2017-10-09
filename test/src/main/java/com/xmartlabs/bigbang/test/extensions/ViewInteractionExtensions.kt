package com.xmartlabs.bigbang.test.extensions

import android.support.annotation.IdRes
import android.support.test.espresso.ViewInteraction

import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.view.View
import com.xmartlabs.bigbang.test.assertions.RecyclerViewAssertions
import com.xmartlabs.bigbang.test.assertions.SwipeToRefreshAssertions
import com.xmartlabs.bigbang.test.viewaction.MaterialPickerDialogActions
import com.xmartlabs.bigbang.test.viewaction.nestedScrollViewScrollTo
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not

fun ViewInteraction.checkDoesNotExist(): ViewInteraction = check(doesNotExist())

fun ViewInteraction.checkHasDescendentWithText(@IdRes textResId: Int): ViewInteraction =
    check(matches(hasDescendant(ViewMatchers.withText(textResId))))

fun ViewInteraction.checkHasDescendantWithText(text: String): ViewInteraction =
    check(matches(hasDescendant(ViewMatchers.withText(text))))

fun ViewInteraction.checkHasText(@IdRes textResId: Int): ViewInteraction =
    check(matches(ViewMatchers.withText(textResId)))

fun ViewInteraction.checkHasText(text: String): ViewInteraction =
    check(matches(ViewMatchers.withText(text)))

fun ViewInteraction.checkIsDisplayed(): ViewInteraction = check(matches(isDisplayed()))

fun ViewInteraction.checkIsNotDisplayed(): ViewInteraction = check(matches(not(isDisplayed())))

fun ViewInteraction.checkRecyclerViewAtPosition(index: Int, itemMatcher: Matcher<View>): ViewInteraction =
    check(ViewAssertions.matches(RecyclerViewAssertions.atPosition(index, itemMatcher)))

fun ViewInteraction.checkRecyclerViewCountIs(index: Int): ViewInteraction =
    check(ViewAssertions.matches(RecyclerViewAssertions.countIs(index)))

fun ViewInteraction.performClick(): ViewInteraction = perform(click())

fun ViewInteraction.performPullDownToRefresh(): ViewInteraction = perform(
    SwipeToRefreshAssertions.withCustomConstraints(ViewActions.swipeDown(), ViewMatchers.isDisplayingAtLeast(85)))

fun ViewInteraction.performScrollInNestedScrollView(): ViewInteraction = perform(nestedScrollViewScrollTo())

fun ViewInteraction.performSetDateMaterialPickerDialog(year: Int, monthOfYear: Int, dayOfMonth: Int): ViewInteraction =
    perform(MaterialPickerDialogActions.setDate(year, monthOfYear, dayOfMonth))

fun ViewInteraction.performSetTimeMaterialPickerDialog(hours: Int, minutes: Int): ViewInteraction =
    perform(MaterialPickerDialogActions.setTime(hours, minutes))
