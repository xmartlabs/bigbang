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
  public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING);

  @Before
  public void setUp() {
    TimeZone.setDefault(DEFAULT_TIME_ZONE);
  }

  @Test
  public void getListOfDaysBetweenTwoDates() {
    LocalDate startDate = LocalDate.of(2016, Month.AUGUST, 22);
    LocalDate endDate = LocalDate.of(2016, Month.AUGUST, 29);
    List<LocalDate> expectedList = IntStream.range(22, 30)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates = DateHelper.getListOfDaysBetweenTwoDates(startDate, endDate);

    assertThat(expectedList, equalTo(listOfDaysBetweenTwoDates));
  }

  @Test
  public void getListOfDaysBetweenTwoWeeks() {
    LocalDate firstDayOfWeek = LocalDate.of(2016, Month.AUGUST, 22);
    LocalDate end = firstDayOfWeek.plusWeeks(1).plusDays(-1);

    List<LocalDate> expectedList = IntStream.range(22, 29)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates = DateHelper.getListOfDaysBetweenTwoDates(firstDayOfWeek, end);

    assertThat(expectedList, equalTo(listOfDaysBetweenTwoDates));
  }

  @Test
  public void getFirstDayOfWeekMonday() {
    LocalDate expectedMonday = LocalDate.of(2017, Month.APRIL, 10);
    LocalDate monday = LocalDate.of(2017, Month.APRIL, 10);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(monday);

    assertThat(actualMonday, equalTo(expectedMonday));
  }

  @Test
  public void getFirstDayOfWeekThursday() {
    LocalDate expectedMonday = LocalDate.of(2017, Month.APRIL, 10);
    LocalDate thursday = LocalDate.of(2017, Month.APRIL, 13);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(thursday);

    assertThat(actualMonday, equalTo(expectedMonday));
  }

  @Test
  public void getFirstDayOfWeekPreviousWeek() {
    LocalDate notExpectedMonday = LocalDate.of(2017, Month.APRIL, 10);
    LocalDate thursday = LocalDate.of(2017, Month.APRIL, 9);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(thursday);

    assertThat(actualMonday, not(equalTo(notExpectedMonday)));
  }

  @Test
  public void getFirstDayOfWeekNextWeek() {
    LocalDate notExpectedMonday = LocalDate.of(2017, Month.APRIL, 10);
    LocalDate tuesdayNext = LocalDate.of(2017, Month.APRIL, 18);
    LocalDate actualMonday = DateHelper.getFirstDayOfWeek(tuesdayNext);

    assertThat(actualMonday, not(equalTo(notExpectedMonday)));
  }

  @Test
  public void isSameDaySameStartEnd() {
    LocalDateTime testDate = LocalDateTime.of(2017, Month.APRIL, 10, 10, 10, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(testDate, testDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayNormal() {
    LocalDateTime startDate = LocalDateTime.of(2017, Month.APRIL, 10, 10, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2017, Month.APRIL, 10, 15, 0, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(startDate, endDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayExtremes() {
    LocalDateTime dateToQuestion = LocalDateTime.of(2017, Month.APRIL, 10, 0, 0, 0);
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 10, 23, 59, 59);
    boolean actual = DateHelper.isSameDayIncludingMidnight(dateToQuestion, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayLatest() {
    LocalDateTime dateToQuestion = LocalDateTime.of(2017, Month.APRIL, 10, 10, 0, 0);
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 10, 23, 59, 59);
    boolean actual = DateHelper.isSameDayIncludingMidnight(dateToQuestion, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isSameDayIncludingMidnight() {
    LocalDateTime dateToQuestion = LocalDateTime.of(2017, Month.APRIL, 10, 0, 0, 0);
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 11, 0, 0, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(dateToQuestion, actualDate);
    assertThat(actual, is(true));
  }

  @Test
  public void isNotDay() {
    LocalDateTime dateToQuestion = LocalDateTime.of(2017, Month.APRIL, 10, 10, 0, 0);
    LocalDateTime actualDate = LocalDateTime.of(2017, Month.APRIL, 11, 0, 10, 0);
    boolean actual = DateHelper.isSameDayIncludingMidnight(dateToQuestion, actualDate);
    assertThat(actual, is(false));
  }

  @Test
  public void correctDateToLocalDate() {
    LocalDate expectedLocalDate = LocalDate.of(2017, Month.APRIL, 10);
    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, Calendar.APRIL, 10, 10, 0, 0);
    Date actualDate = calendar.getTime();

    LocalDate actualLocalDate = DateHelper.dateToLocalDate(actualDate);
    assertThat(actualLocalDate, equalTo(expectedLocalDate));
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
