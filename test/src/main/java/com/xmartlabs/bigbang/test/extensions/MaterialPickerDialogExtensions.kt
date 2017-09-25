package com.xmartlabs.bigbang.test.extensions

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.util.Log
import android.view.View
import com.wdullaer.materialdatetimepicker.date.DatePickerController
import com.wdullaer.materialdatetimepicker.date.DayPickerView
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import com.wdullaer.materialdatetimepicker.time.Timepoint
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object MaterialPickerDialogActions {
  fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int): ViewAction {
    return object : ViewAction {
      override fun perform(uiController: UiController, view: View) {
        val dayPickerView = view as DayPickerView

        try {
          val field = DayPickerView::class.java.getDeclaredField("mController")
          field.isAccessible = true
          val controller = field.get(dayPickerView) as DatePickerController
          controller.onDayOfMonthSelected(year, monthOfYear, dayOfMonth)
        } catch (exception: Exception) {
          Log.d(MaterialPickerDialogActions.javaClass.canonicalName, exception.toString())
        }
      }

      override fun getDescription(): String = "set date"

      override fun getConstraints(): Matcher<View> =
          allOf<View>(isAssignableFrom(DayPickerView::class.java), isDisplayed())
    }
  }

  fun setTime(hours: Int, minutes: Int): ViewAction {
    return object : ViewAction {
      override fun perform(uiController: UiController, view: View) {
        val timePicker = view as RadialPickerLayout
        timePicker.time = Timepoint(hours, minutes, 0)
      }

      override fun getDescription(): String = "set time"

      override fun getConstraints(): Matcher<View> =
          allOf<View>(isAssignableFrom(RadialPickerLayout::class.java), isDisplayed())
    }
  }
}

fun ViewInteraction.performSetDateMaterialPickerDialog(year: Int, monthOfYear: Int, dayOfMonth: Int): ViewInteraction =
    perform(MaterialPickerDialogActions.setDate(year, monthOfYear, dayOfMonth))

fun ViewInteraction.performSetTimeMaterialPickerDialog(hours: Int, minutes: Int): ViewInteraction =
    perform(MaterialPickerDialogActions.setTime(hours, minutes))
