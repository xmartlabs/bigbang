package com.xmartlabs.base.core;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;
import com.xmartlabs.base.core.helper.DateHelper;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class DateHelperTest {
  private static final String DEFAULT_TIME_ZONE_STRING = "GMT-03:00";
  private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING);
  private static final LocalDate LOCAL_DATE_22nd_AUG_2016 = LocalDate.of(2016, Month.AUGUST, 22);
  private static final LocalDate LOCAL_DATE_29th_AUG_2016 = LocalDate.of(2016, Month.AUGUST, 29);
  private static final LocalDate LOCAL_DATE_MONDAY_10TH_APR_2017 = LocalDate.of(2017, Month.APRIL, 10);
  private static final LocalDateTime LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS = LocalDateTime.of(2017, Month.APRIL, 10, 10, 10, 0);

  @Before
  public void setUp() {
    TimeZone.setDefault(DEFAULT_TIME_ZONE);
  }

  @Test
  public void getListOfDaysBetweenTwoDates() {
    List<LocalDate> expectedList = IntStream.range(22, 30)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates =
        DateHelper.getListOfDaysBetweenTwoDates(LOCAL_DATE_22nd_AUG_2016, LOCAL_DATE_29th_AUG_2016);

    assertThat(expectedList, equalTo(listOfDaysBetweenTwoDates));
  }

  @Test
  public void getListOfDaysBetweenTwoWeeks() {
    LocalDate firstDayOfWeek = LOCAL_DATE_22nd_AUG_2016;
    LocalDate end = firstDayOfWeek.plusWeeks(1).plusDays(-1);

    List<LocalDate> expectedList = IntStream.range(22, 29)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates = DateHelper.getListOfDaysBetweenTwoDates(firstDayOfWeek, end);

    assertThat(expectedList, equalTo(listOfDaysBetweenTwoDates));
  }

  @Test
  public void getFirstDayOfWeekMonday() {
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(LOCAL_DATE_MONDAY_10TH_APR_2017);

    assertThat(actualMonday, equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017));
  }

  @Test
  public void getFirstDayOfWeekThursday() {
    LocalDate thursday = LocalDate.of(2017, Month.APRIL, 13);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(thursday);

    assertThat(actualMonday, equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017));
  }

  @Test
  public void getFirstDayOfWeekPreviousWeek() {
    LocalDate thursday = LocalDate.of(2017, Month.APRIL, 9);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(thursday);

    assertThat(actualMonday, not(equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017)));
  }

  @Test
  public void getFirstDayOfWeekNextWeek() {
    LocalDate tuesdayNext = LocalDate.of(2017, Month.APRIL, 18);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(tuesdayNext);

    assertThat(actualMonday, not(equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017)));
  }

  @Test
  public void isSameDaySameStartEnd() {
    LocalDateTime testDate = LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS;
    boolean actual = DateHelper.isSameDayIncludingMidnight(testDate, testDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayNormal() {
    LocalDateTime endDate = LocalDateTime.of(2017, Month.APRIL, 10, 15, 0, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS, endDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayExtremes() {
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 10, 23, 59, 59);
    boolean actual = DateHelper.isSameDayIncludingMidnight(LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayLatest() {
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 10, 23, 59, 59);
    boolean actual = DateHelper.isSameDayIncludingMidnight(LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayIncludingMidnight() {
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 11, 0, 0, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isNotDay() {
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 11, 0, 10, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(LOCAL_DATE_TIME_MONDAY_APRIL_10TH_10_HRS, actualDate);
    assertThat(actual, is(false));
  }

  @Test
  public void correctDateToLocalDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, Calendar.APRIL, 10, 10, 0, 0);
    Date actualDate = calendar.getTime();

    LocalDate actualLocalDate = DateHelper.dateToLocalDate(actualDate);
    assertThat(actualLocalDate, equalTo(LOCAL_DATE_MONDAY_10TH_APR_2017));
  }

  @Test
  public void correctDateToLocalDateTime() {
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2017, Month.APRIL, 10, 10, 10, 10);
    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, Calendar.APRIL, 10, 10, 10, 10);
    calendar.set(Calendar.MILLISECOND, 0);
    Date actualDate = calendar.getTime();

    LocalDateTime actualLocalDate = DateHelper.dateToLocalDateTime(actualDate);
    assertThat(actualLocalDate, equalTo(expectedLocalDateTime));
  }

  @Test
  public void correctDateToLocalTime() {
    LocalTime expectedLocalTime = LocalTime.of(10, 10, 10);
    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, Calendar.APRIL, 10, 10, 10, 10);
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();

    LocalTime actualLocalTime = DateHelper.dateToLocalTime(date);
    assertThat(actualLocalTime, equalTo(expectedLocalTime));
  }

  @Test
  public void correctCalendarToZonedDateTime() {
    ZonedDateTime expectedZonedDateTime = ZonedDateTime.ofLocal(LocalDateTime.of(2017, Month.APRIL, 10, 10, 10, 10),
        ZoneId.of("GMT-03:00"), null);

    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, Calendar.APRIL, 10, 10, 10, 10);
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

    ZonedDateTime actualZonedDateTime = DateHelper.calendarToZonedDateTime(calendar);

    assertThat(actualZonedDateTime, equalTo(expectedZonedDateTime));
  }
}
