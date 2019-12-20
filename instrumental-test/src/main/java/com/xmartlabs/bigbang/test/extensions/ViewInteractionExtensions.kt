package com.xmartlabs.bigbang.test.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.xmartlabs.bigbang.test.assertions.RecyclerViewAssertions
import com.xmartlabs.bigbang.test.assertions.SwipeToRefreshAssertions
import com.xmartlabs.bigbang.test.viewaction.MaterialPickerDialogActions
import com.xmartlabs.bigbang.test.viewaction.NestedScrollViewViewAction
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

/**
 * Checks that the matcher specified in this [ViewInteraction] does not find any view
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkDoesNotExist() = check(doesNotExist())

/**
 * Check that the matcher specified in this [ViewInteraction] has a descendant TextView with the
 * text `textResId` resource
 *
 * @param textResId the text resource to find in the descendant
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkHasDescendantWithText(@IdRes textResId: Int) =
    check(matches(hasDescendant(ViewMatchers.withText(textResId))))

/**
 * Check that the matcher specified in this [ViewInteraction] has a descendant TextView whose text property value
 * equals `text`
 *
 * @param text the text to find in the descendant
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkHasDescendantWithText(text: String) =
    check(matches(hasDescendant(ViewMatchers.withText(text))))

/**
 * Checks that the matcher specified in this [ViewInteraction] is a TextView with the text `textResId` resource
 *
 * @param textResId the text to match against with
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkHasText(@IdRes textResId: Int) =
    check(matches(ViewMatchers.withText(textResId)))

/**
 * Checks that the matcher specified in this [ViewInteraction] is a TextView whose text value property matches `text`
 *
 * @param text the text to match against with
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkHasText(text: String) =
    check(matches(ViewMatchers.withText(text)))

/**
 * Checks that the matcher specified in this [ViewInteraction] is being displayed on screen
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkIsDisplayed() = check(matches(isDisplayed()))

/**
 * Checks that the matcher specified in this [ViewInteraction] is not being displayed on the screen
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkIsNotDisplayed() = check(matches(not(isDisplayed())))

/**
 * Checks that the view that matches with `itemMatcher` is a view inside a RecyclerView in the `position`
 * specified
 *
 * @param position the position of the view
 * @param itemMatcher the view to check if it's in the `position`
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkRecyclerViewAtPosition(position: Int, itemMatcher: Matcher<View>) =
    check(ViewAssertions.matches(RecyclerViewAssertions.atPosition(position, itemMatcher)))

/**
 * Checks that the RecyclerView has `amountOfViews` views
 *
 * @param amountOfViews the number of views that should be inside the RecyclerView
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.checkRecyclerViewCountIs(amountOfViews: Int) =
    check(ViewAssertions.matches(RecyclerViewAssertions.countIs(amountOfViews)))

/**
 * Performs a click on the view specified in this [ViewInteraction]
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performClick() = perform(click())

/**
 * Performs a pull down to refresh action on the view specified in this [ViewInteraction]
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performPullDownToRefresh() = perform(
    SwipeToRefreshAssertions.withCustomConstraints(
        ViewActions.swipeDown(),
        ViewMatchers.isDisplayingAtLeast(85)
    )
)

/**
 * Performs a scroll in the NestedScrollView specified in this [ViewInteraction]
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performScrollInNestedScrollView() = perform(NestedScrollViewViewAction.scrollTo())

/**
 * Sets the date in the material date picker specified in this [ViewInteraction]
 *
 * @param year the year to set
 * @param monthOfYear the month to set
 * @param dayOfMonth the day of month to set
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performSetDateMaterialPickerDialog(year: Int, monthOfYear: Int, dayOfMonth: Int) =
    perform(MaterialPickerDialogActions.setDate(year, monthOfYear, dayOfMonth))

/**
 * Sets the date in the material date picker specified in this [ViewInteraction]
 *
 * @param localDate the date to set
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performSetDateMaterialPickerDialog(localDate: LocalDate) =
    perform(MaterialPickerDialogActions.setDate(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth))

/**
 * Sets the time in the material time picker specified in this [ViewInteraction]
 *
 * @param hours the hour to set (in 24h format)
 * @param minutes the minutes to set
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performSetTimeMaterialPickerDialog(hours: Int, minutes: Int) =
    perform(MaterialPickerDialogActions.setTime(hours, minutes))

/**
 * Sets the time in the material time picker specified in this [ViewInteraction]
 *
 * @param localTime the time to set
 *
 * @return this interaction for further perform/verification calls.
 */
fun ViewInteraction.performSetTimeMaterialPickerDialog(localTime: LocalTime) =
    perform(MaterialPickerDialogActions.setTime(localTime.hour, localTime.minute))
