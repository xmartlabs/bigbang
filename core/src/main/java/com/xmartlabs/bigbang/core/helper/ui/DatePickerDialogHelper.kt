package com.xmartlabs.bigbang.core.helper.ui

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.threeten.bp.Clock
import org.threeten.bp.LocalDate

object DatePickerDialogHelper {
  /**
   * Creates a [DatePickerDialog] instance.
   *
   * @param listener to be triggered when the user selects a date
   * *
   * @param clock to get the current date
   * *
   * @return the [DatePickerDialog] created instance
   */
  fun createDialog(listener: ((LocalDate) -> Unit)?, clock: Clock) = createDialog(LocalDate.now(clock), listener)

  /**
   * Creates a [DatePickerDialog] instance with the `localDate` selected.
   *
   * @param localDate the selected start localDate
   * *
   * @param listener to be triggered when the user selects a localDate
   * *
   * @return the [DatePickerDialog] created instance
   */
  fun createDialog(localDate: LocalDate, listener: ((LocalDate) -> Unit)?): DatePickerDialog {
    val dialogCallBack = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
      listener?.let { it(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) }
    }

    val datePickerDialog = DatePickerDialog
        .newInstance(dialogCallBack, localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
    datePickerDialog.dismissOnPause(true)
    return datePickerDialog
  }
}
