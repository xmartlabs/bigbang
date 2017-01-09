package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.annimon.stream.Collectors;
import com.annimon.stream.Objects;
import com.annimon.stream.Stream;
import com.xmartlabs.template.BaseProjectApplication;

import org.threeten.bp.Clock;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.TemporalAccessor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by santiago on 18/09/15.
 * A helper class to conveniently parse date information.
 * Based on the version of Ralf Gehrer <ralf@ecotastic.de>.
 * This class contains date parsing for human and machine readable dates.
 */
@SuppressWarnings("unused")
public class DateHelper {
  private static final String DATE_COMPLETE = "yyyy-MMM-dd HH:mm:ss";
  @SuppressWarnings("SpellCheckingInspection")
  private static final String DATE_SHORT = "EEEE, MMM d";

  public static final String DATE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  private static final DateTimeFormatter DATE_COMPLETE_FORMAT = DateTimeFormatter.ofPattern(DATE_COMPLETE, Locale.US);
  public static final DateTimeFormatter DATE_ISO_8601_FORMAT = DateTimeFormatter.ofPattern(DATE_ISO_8601, Locale.US);
  public static final DateTimeFormatter DATE_SHORT_FORMAT = DateTimeFormatter.ofPattern(DATE_SHORT, Locale.US);
  public static final DateTimeFormatter DATE_SHORT_MONTH_NAME_FORMAT = DateTimeFormatter.ofPattern("MMM", Locale.US);
  public static final DateTimeFormatter DATE_DAY_FORMAT = DateTimeFormatter.ofPattern("EEE d", Locale.US);
  public static final String DATE_SEPARATOR = " - ";

  public static final DateTimeFormatter DATE_TIME_FORMATTER_SHORT = DateTimeFormatter.ofPattern(DATE_SHORT);
  @SuppressWarnings("SpellCheckingInspection")
  public static final DateTimeFormatter DATE_TIME_FORMATTER_WEEK_DAY_LETTER = DateTimeFormatter.ofPattern("EEEEE");

  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
  public static final DateTimeFormatter HOUR_MINUTE_FORMAT = DateFormat.is24HourFormat(BaseProjectApplication.getContext())
      ? DateTimeFormatter.ofPattern("H:mm", Locale.getDefault())
      : DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());

  /**
   * Gets the android device time format.
   *
   * @return DateTimeFormatter formatted on the right way.
   */
  public static DateTimeFormatter getDeviceTimeFormat() {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getTimeFormat(BaseProjectApplication.getContext());
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Gets the android device medium date format.
   *
   * @return DateTimeFormatter formatted on the right way.
   */
  public static DateTimeFormatter getDeviceMediumDateFormat() {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getMediumDateFormat(BaseProjectApplication.getContext());
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Gets the android device long date format.
   *
   * @return DateTimeFormatter formatted on the right way.
   */
  public static DateTimeFormatter getDeviceLongDateFormat() {
    SimpleDateFormat timeFormat = (SimpleDateFormat) DateFormat.getLongDateFormat(BaseProjectApplication.getContext());
    return DateTimeFormatter.ofPattern(timeFormat.toPattern());
  }

  /**
   * Converts a TemporalAccessor object to a string representation.
   *
   * @param temporalAccessor TemporalAccessor object.
   * @param df               Date format.
   * @return Date string formatted on the right way.
   */
  @NonNull
  public static String dateToString(@NonNull TemporalAccessor temporalAccessor, @NonNull DateTimeFormatter df) {
    return df.format(temporalAccessor);
  }

  /**
   * Converts a string representation of a date to its LocalDateTime object.
   *
   * @param dateAsString Date in string format.
   * @param df           Date format.
   * @return Date.
   */
  @Nullable
  public static LocalDateTime stringToLocalDateTime(@NonNull String dateAsString, @NonNull DateTimeFormatter df) {
    try {
      return LocalDateTime.parse(dateAsString, df);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Sets the <code>date</code> to the last second of the day it's in.
   *
   * @param date the date to update.
   * @return a new <code>Date</code>, same day as <code>date</code> but starting at the last second of the day.
   */
  @NonNull
  public static LocalDateTime toLastSecond(@NonNull LocalDateTime date) {
    return date.with(LocalTime.MAX);
  }

  /**
   * Checks whether the given <code>date</code> is today or not.
   *
   * @param date  the date to check.
   * @param clock to be used to retrieve today's date.
   * @return true if the date given by the <code>clock</code> is the same as <code>date</code>.
   */
  public static boolean isToday(LocalDate date, Clock clock) {
    return Objects.equals(date, LocalDate.now(clock));
  }

  /**
   * Retrieves the first day of the <code>date</code> week.
   *
   * @param date the date whose week's first day we want to retrieve.
   * @return a <code>Date</code> instance representing the first day of the <code>date</code> week.
   */
  public static LocalDate getFirstDayOfWeek(LocalDate date) {
    return date.with(DayOfWeek.MONDAY);
  }

  /**
   * Retrieves a <code>Calendar</code> instance from the <code>clock</code>.
   *
   * @param clock to be used to retrieve the <code>Calendar</code> instance.
   * @return a new <code>Calendar</code> instance representing the clock's time.
   */
  @NonNull
  public static Calendar getCalendarFromClock(Clock clock) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(clock.millis());
    return calendar;
  }

  /**
   * Retrieves a <code>Date</code> instance from the <code>clock</code>.
   *
   * @param clock to be used to retrieve the <code>Date</code> instance.
   * @return a new <code>Date</code> omstamce representing the clock's time.
   */
  @NonNull
  public static Date getDateFromClock(Clock clock) {
    return new Date(clock.millis());
  }

  /**
   * Checks whether or not the <code>startDate</code> is the same day as <code>endDate</code>, including midnight
   * time.
   *
   * @param startDate the date to check.
   * @param endDate   the date to check against to.
   * @return true if <code>startDate</code> is the same date as <code>endDate</code> or <code>endDate</code> is the
   * midnight of the next day.
   */
  public static boolean isSameDayIncludingMidnight(LocalDateTime startDate, LocalDateTime endDate) {
    return startDate.toLocalDate().equals(endDate.toLocalDate())
        || startDate.toLocalDate().equals(endDate.plusSeconds(-10).toLocalDate());
  }

  /**
   * Convert an instance of the <code>LocalDateTime</code> Java 8 Time backport to a <code>Date</code> instance.
   *
   * @param localDateTime the date to be converted.
   * @return a new <code>Date</code> instance representing the same date as <code>localDateTime</code>.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateTimeToDate(@NonNull LocalDateTime localDateTime) {
    //noinspection deprecation
    return localDateTimeToDate(localDateTime, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the <code>LocalDateTime</code> Java 8 Time backport to a <code>Date</code> instance.
   *
   * @param localDateTime the date to be converted.
   * @param zoneId        the rules to be used.
   * @return a new <code>Date</code> instance representing the same date as <code>localDateTime</code>.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateTimeToDate(@NonNull LocalDateTime localDateTime, @NonNull ZoneId zoneId) {
    //noinspection deprecation
    return localDateToDate(LocalDate.from(localDateTime), zoneId);
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Date</code> instance.
   *
   * @param localDate the date to be converted.
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code>.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate) {
    //noinspection deprecation
    return localDateToDate(localDate, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Date</code> instance,
   * using the rules given by <code>zoneId</code>.
   *
   * @param localDate the date to be converted.
   * @param zoneId    the rules to be used.
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code> using the
   * <code>zoneId</code> rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Date localDateToDate(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toDate(localDate.atStartOfDay(zoneId).toInstant());
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Calendar</code> instance.
   *
   * @param localDate the date to be converted.
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code>.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateToCalendar(@NonNull LocalDate localDate) {
    //noinspection deprecation
    return localDateToCalendar(localDate, ZoneId.systemDefault());
  }

  /**
   * Convert an instance of the <code>LocalDate</code> Java 8 Time backport to a <code>Calendar</code> instance,
   * using the rules given by <code>zoneId</code>.
   *
   * @param localDate the date to be converted.
   * @param zoneId    the rules to be used.
   * @return a new <code>Date</code> instance representing the same date as <code>localDate</code> using the
   * <code>zoneId</code> rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateToCalendar(@NonNull LocalDate localDate, @NonNull ZoneId zoneId) {
    return DateTimeUtils.toGregorianCalendar(localDate.atStartOfDay(zoneId));
  }

  /**
   * Convert an instance of the <code>LocalDateTime</code> Java 8 Time backport to a <code>Calendar</code> instance,
   * using the rules given by <code>zoneId</code>.
   *
   * @param localDateTime the date to be converted.
   * @param zoneId        the rules to be used.
   * @return a new <code>Date</code> instance representing the same date as <code>localDateTime</code> using the
   * <code>zoneId</code> rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateTimeToCalendar(@NonNull LocalDateTime localDateTime, @NonNull ZoneId zoneId) {
    //noinspection deprecation
    return localDateToCalendar(LocalDate.from(localDateTime), zoneId);
  }

  /**
   * Convert an instance of the <code>LocalDateTime</code> Java 8 Time backport to a <code>Calendar</code> instance,
   * using the rules given by <code>zoneId</code>.
   *
   * @param localDateTime the date to be converted.
   * @return a new <code>Date</code> instance representing the same date as <code>localDateTime</code> using the
   * <code>zoneId</code> rules.
   * @deprecated Use it only for third party libraries.
   */
  @Deprecated
  @NonNull
  public static Calendar localDateTimeToCalendar(@NonNull LocalDateTime localDateTime) {
    //noinspection deprecation
    return localDateTimeToCalendar(localDateTime, ZoneId.systemDefault());
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>LocalDate</code>.
   *
   * @param date the date to be converted.
   * @return a new <code>LocalDate</code> instance representing the same date as <code>date</code>.
   */
  @NonNull
  public static LocalDate dateToLocalDate(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDate();
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>LocalDateTime</code>.
   *
   * @param date the date to be converted.
   * @return a new <code>LocalDateTime</code> instance representing the same date as <code>date</code>.
   */
  @NonNull
  public static LocalDateTime dateToLocalDateTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalDateTime();
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>LocalTime</code>.
   *
   * @param date the date to be converted.
   * @return a new <code>LocalTime</code> instance representing the same date as <code>date</code>.
   */
  @NonNull
  public static LocalTime dateToLocalTime(@NonNull Date date) {
    return dateToZonedDateTime(date).toLocalTime();
  }

  /**
   * Convert a <code>Calendar</code> to an instance of Java 8 Time backport <code>ZonedDateTime</code>.
   *
   * @param calendar the date to be converted.
   * @return a new <code>ZonedDateTime</code> instance representing the same date as <code>calendar</code>, using the
   * system default's <code>ZoneId</code>.
   */
  @NonNull
  public static ZonedDateTime calendarToZonedDateTime(@NonNull Calendar calendar) {
    return DateTimeUtils.toInstant(calendar).atZone(ZoneId.systemDefault());
  }

  /**
   * Convert a <code>Date</code> to an instance of Java 8 Time backport <code>ZonedDateTime</code>.
   *
   * @param date the date to be converted.
   * @return a new <code>ZonedDateTime</code> instance representing the same date as <code>date</code>, using the
   * system default's <code>ZoneId</code>.
   */
  @NonNull
  private static ZonedDateTime dateToZonedDateTime(@NonNull Date date) {
    return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault());
  }

  /**
   * Retrieves a list of days (<code>LocalDate</code>s) between <code>startDate</code> and <code>endDate</code>.
   *
   * @param startDate the date before the list should start.
   * @param endDate   the date after the list should end.
   * @return the list between, but not including, <code>startDate</code> and <code>endDate</code>.
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
    LocalDate endLocalDate = endDate.plusDays(1);
    return Stream.iterate(startDate, day -> day.plusDays(1))
        .limit(startDate.until(endLocalDate, ChronoUnit.DAYS))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves a list of days (<code>LocalDate</code>s) between <code>startDate</code> and <code>endDate</code>.
   *
   * @param startDate the date before the list should start.
   * @param endDate   the date after the list should end.
   * @return the list between, but not including, <code>startDate</code> and <code>endDate</code>.
   */
  @NonNull
  public static List<LocalDate> getListOfDaysBetweenTwoDates(Date startDate, Date endDate) {
    return getListOfDaysBetweenTwoDates(dateToLocalDate(startDate), dateToLocalDate(endDate));
  }
}
