package com.xmartlabs.bigbang.test.viewaction

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.wdullaer.materialdatetimepicker.date.DatePickerController
import com.wdullaer.materialdatetimepicker.date.DayPickerView
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.Timepoint
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import timber.log.Timber

/**
 * View action helpers for the Material Dialog Picker
 */
object MaterialPickerDialogActions {
  /**
   * Creates the [ViewAction] that can set the date on the date picker
   *
   * @param year the year to set
   * @param monthOfYear the month to set
   * @param dayOfMonth the day of month to set
   *
   * @return the [ViewAction] that sets the specified date
   */
  fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) = object : ViewAction {
    override fun perform(uiController: UiController, view: View) {
      val dayPickerView = view as DayPickerView
    
      try {
        val field = DayPickerView::class.java.getDeclaredField("mController")
        field.isAccessible = true
        val controller = field.get(dayPickerView) as DatePickerController
        controller.onDayOfMonthSelected(year, monthOfYear, dayOfMonth)
      } catch (exception: Exception) {
        Timber.d(exception)
      }
    }
  
    override fun getDescription(): String = "set date"
  
    override fun getConstraints(): Matcher<View> =
        allOf<View>(isAssignableFrom(DayPickerView::class.java), isDisplayed())
  }
  
  /**
   * Creates a [ViewAction] that can set the time on the time picker
   *
   * @param hours the hour to set (in 24h format)
   * @param minutes the minutes to set
   *
   * @return the [ViewAction] that sets the specified time
   */
  fun setTime(hours: Int, minutes: Int) = object : ViewAction {
    override fun perform(uiController: UiController, view: View) {
      val timePicker = view as RadialPickerLayout
      timePicker.time = Timepoint(hours, minutes, 0)
    }
  
    override fun getDescription(): String = "set time"
  
    override fun getConstraints(): Matcher<View> =
        allOf<View>(isAssignableFrom(RadialPickerLayout::class.java), isDisplayed())
  }
}
