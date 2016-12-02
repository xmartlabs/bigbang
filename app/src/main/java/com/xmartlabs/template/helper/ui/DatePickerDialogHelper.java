package com.xmartlabs.template.helper.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.xmartlabs.template.helper.DateHelper;

import org.threeten.bp.Clock;
import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.Setter;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class DatePickerDialogHelper {
  /**
   * Creates a <code>DatePickerDialog</code> instance
   * @param listener to be triggered when the user selects a date
   * @param clock to get the <code>Calendar</code> from
   * @return the <code>DatePickerDialog</code> created instance
   */
  @SuppressWarnings("unused")
  @NonNull
  public static DatePickerDialog createDialog(@Nullable OnLocalDateSetListener listener, @NonNull Clock clock) {
    return createDialog(null, listener, clock);
  }

  /**
   * Creates a <code>DatePickerDialog</code> instance with the <code>localDate</code> selected
   * @param localDate the selected start localDate
   * @param listener to be triggered when the user selects a localDate
   * @param clock to get the <code>Calendar</code> from
   * @return the <code>DatePickerDialog</code> created instance
   */
  @NonNull
  public static DatePickerDialog createDialog(@Nullable LocalDate localDate, @Nullable OnLocalDateSetListener listener,
                                              @NonNull Clock clock) {
    if (localDate == null) {
      localDate = LocalDate.now(clock);
    }

    DatePickerDialog.OnDateSetListener dialogCallBack = (view, year, monthOfYear, dayOfMonth) -> {
      LocalDate date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
      if (listener != null) {
        listener.onDateSet(date);
      }
    };

    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
        dialogCallBack,
        localDate.getYear(),
        localDate.getMonthValue() - 1,
        localDate.getDayOfMonth()
    );
    datePickerDialog.dismissOnPause(true);
    return datePickerDialog;
  }
}
