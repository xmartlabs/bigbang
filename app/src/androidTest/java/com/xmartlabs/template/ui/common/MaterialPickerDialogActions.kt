package com.xmartlabs.template.ui.common

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.view.View
import com.wdullaer.materialdatetimepicker.date.DatePickerController
import com.wdullaer.materialdatetimepicker.date.DayPickerView
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.Timepoint
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import timber.log.Timber

/**
 * Copied from http://stackoverflow.com/a/34673382/1165181.
 */
object MaterialPickerDialogActions {
  fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int) = object : ViewAction {
    override fun perform(uiController: UiController, view: View) {
      val dayPickerView = view as DayPickerView
      
      try {
        val field = DayPickerView::class.java.getDeclaredField("mController")
        field.isAccessible = true
        val controller = field.get(dayPickerView) as DatePickerController
        controller.onDayOfMonthSelected(year, monthOfYear, dayOfMonth)
      } catch (e: Exception) {
        Timber.e(e)
      }
    }
    
    override fun getDescription() = "set date"
    
    override fun getConstraints() = allOf(isAssignableFrom(DayPickerView::class.java), isDisplayed())
  }
}

fun setTime(hours: Int, minutes: Int) = object : ViewAction {
  override fun perform(uiController: UiController, view: View) {
    val timePicker = view as RadialPickerLayout
    
    timePicker.time = Timepoint(hours, minutes, 0)
  }
  
  override fun getDescription(): String {
    return "set time"
  }
  
  override fun getConstraints(): Matcher<View> {
    return allOf(isAssignableFrom(RadialPickerLayout::class.java), isDisplayed())
  }
}
