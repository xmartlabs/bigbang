package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by santiago on 18/09/15.
 * A helper class to conveniently parse date information.
 * Based on the version of Ralf Gehrer <ralf@ecotastic.de>
 * This class contains date parsing for human and machine readable dates.
 */
public class DateHelper {
  private static final String DATE_MDY = "MM/dd/yyyy";
  public static final String DATE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final DateFormat DATE_MDY_FORMAT = new SimpleDateFormat(DATE_MDY, Locale.US);
  public static final DateFormat DATE_ISO_8601_FORMAT = new SimpleDateFormat(DATE_ISO_8601, Locale.US);

  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

  static {
    DATE_ISO_8601_FORMAT.setTimeZone(UTC);
  }

  /**
   * Converts a Date object to a string representation in UTC
   *
   * @param date Date object
   * @param df   Date format
   * @return Date string formatted on the right way in UTC
   */
  @Nullable
  public static String dateToStringUTC(@Nullable Date date, @NonNull DateFormat df) {
    if (date == null) {
      return null;
    } else {
      df.setTimeZone(UTC);
      return dateToString(date, df);
    }
  }

  /**
   * Converts a Date object to a string representation
   *
   * @param date Date object
   * @param df   Date format
   * @return Date string formatted on the right way
   */
  @NonNull
  public static String dateToString(@NonNull Date date, @NonNull DateFormat df) {
    return df.format(date);
  }

  /**
   * Converts a string representation of a date to its Date object.
   *
   * @param dateAsString Date in string format
   * @param df           Date format
   * @return Date
   */
  @Nullable
  public static Date stringToDate(@NonNull String dateAsString, @NonNull DateFormat df) {
    try {
      df.setTimeZone(UTC);
      return df.parse(dateAsString);
    } catch (ParseException e) {
      return null;
    }
  }

  public static int getAge(@NonNull Date dateOfBirth) {
    Calendar dob = Calendar.getInstance();
    dob.setTime(dateOfBirth);
    Calendar today = Calendar.getInstance();
    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
    if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)
        || today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
      age--;
    }
    return age;
  }
}
