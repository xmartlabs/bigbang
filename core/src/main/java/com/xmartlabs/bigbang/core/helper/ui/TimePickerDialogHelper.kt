package com.xmartlabs.bigbang.core.helper.ui

import android.content.Context
import android.text.format.DateFormat
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import org.threeten.bp.Clock
import org.threeten.bp.LocalTime

object TimePickerDialogHelper {
  /**
   * Creates a [TimePickerDialog] instance without specifying the initial date.
   *
   * @param context  to retrieve the time format (12 or 24 hour)
   * *
   * @param listener to listen for a date selection
   * *
   * @param clock    to retrieve the calendar from
   * *
   * @return a new instance of [TimePickerDialog]
   */
  fun createDialog(context: Context, listener: ((LocalTime) -> Unit)?, clock: Clock) =
    createDialog(context, LocalTime.now(clock), listener)

  /**
   * Creates a [TimePickerDialog] instance.
   *
   * @param context   to retrieve the time format (12 or 24 hour)
   * *
   * @param localTime the initial localTime
   * *
   * @param listener  to listen for a localTime selection
   * *
   * @return a new instance of [TimePickerDialog]
   */
  fun createDialog(context: Context, localTime: LocalTime, listener: ((LocalTime) -> Unit)?): TimePickerDialog {
    val dialogCallBack = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, second  ->
      listener?.let { it(LocalTime.of(hourOfDay, minute, second)) }
    }
    
    val timePickerDialog = TimePickerDialog.newInstance(dialogCallBack, localTime.hour, localTime.minute,
        localTime.second, DateFormat.is24HourFormat(context))
    
    timePickerDialog.dismissOnPause(true)
    return timePickerDialog
  }
}
