package com.xmartlabs.template.helper;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by medina on 19/09/2016.
 */
public class DateHelperTest {
  private static final String DEFAULT_TIME_ZONE_STRING = "GMT-03:00";
  public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING);

  @Before
  public void setUp() {
    TimeZone.setDefault(DEFAULT_TIME_ZONE);
  }
  @Test
  public void setDateButKeepTime() {
    Date oldDateAndTime = new GregorianCalendar(2016, 4, 6, 13, 43).getTime();
    Date newDate = new GregorianCalendar(2017, 5, 7, 22, 58).getTime();
    Date expectedDateAndTime = new GregorianCalendar(2017, 5, 7, 13, 43).getTime();
    Date actualDateAndTime = DateHelper.setDateButKeepTime(oldDateAndTime, newDate);
    Assert.assertEquals(expectedDateAndTime, actualDateAndTime);
  }

  @Test
  public void setTimeButKeepDate() {
    Date oldDateAndTime = new GregorianCalendar(2016, 4, 6, 13, 43).getTime();
    Date newTime = new GregorianCalendar(2017, 5, 7, 22, 58).getTime();
    Date expectedDateAndTime = new GregorianCalendar(2016, 4, 6, 22, 58).getTime();
    Date actualDateAndTime = DateHelper.setTimeButKeepDate(oldDateAndTime, newTime);
    Assert.assertEquals(expectedDateAndTime, actualDateAndTime);
  }

  @Test
  public void toFirstSecond() {
    Date date = new GregorianCalendar(2016, 4, 9, 8, 55, 23).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 9, 0, 0, 0).getTime();
    Date actualDate = DateHelper.toFirstSecond(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void toLastSecond() {
    Date date = new GregorianCalendar(2016, 4, 9, 8, 55, 23).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 9, 23, 59, 59).getTime();
    Date actualDate = DateHelper.toLastSecond(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFollowingDay() {
    Date date = new GregorianCalendar(2016, 4, 9, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 10, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFollowingDay(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getPreviousDay() {
    Date date = new GregorianCalendar(2016, 4, 9, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 8, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getPreviousDay(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfAMonday() {
    Date date = new GregorianCalendar(2016, 4, 23, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfATuesday() {
    Date date = new GregorianCalendar(2016, 4, 24, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfAWednesday() {
    Date date = new GregorianCalendar(2016, 4, 25, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfAThursday() {
    Date date = new GregorianCalendar(2016, 4, 26, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfAFriday() {
    Date date = new GregorianCalendar(2016, 4, 27, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfASaturday() {
    Date date = new GregorianCalendar(2016, 4, 28, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getFirstDayOfWeekOfASunday() {
    Date date = new GregorianCalendar(2016, 4, 29, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 23, 0, 0, 0).getTime();
    Date actualDate = DateHelper.getFirstDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfAMonday() {
    Date date = new GregorianCalendar(2016, 4, 23, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfATuesday() {
    Date date = new GregorianCalendar(2016, 4, 24, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfAWednesday() {
    Date date = new GregorianCalendar(2016, 4, 25, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfAThursday() {
    Date date = new GregorianCalendar(2016, 4, 26, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfAFriday() {
    Date date = new GregorianCalendar(2016, 4, 27, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfASaturday() {
    Date date = new GregorianCalendar(2016, 4, 28, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getLastDayOfWeekOfASunday() {
    Date date = new GregorianCalendar(2016, 4, 29, 10, 44, 24).getTime();
    Date expectedDate = new GregorianCalendar(2016, 4, 29, 23, 59, 59).getTime();
    Date actualDate = DateHelper.getLastDayOfWeek(date);
    Assert.assertEquals(expectedDate, actualDate);
  }

  @Test
  public void getDateToLocalDate() {
    Date expected = new GregorianCalendar(2016, 4, 22, 0, 0, 0).getTime();
    LocalDate localDate = DateHelper.dateToLocalDate(expected);
    Date actual = DateHelper.localDateToDate(localDate);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getListOfDaysBetweenTwoDates() {
    LocalDate startDate = LocalDate.of(2016, Month.AUGUST, 22);
    LocalDate endDate = LocalDate.of(2016, Month.AUGUST, 29);
    List<LocalDate> expectedList = IntStream.range(22, 30)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates = DateHelper.getListOfDaysBetweenTwoDates(startDate, endDate);

    Assert.assertEquals(expectedList, listOfDaysBetweenTwoDates);
  }

  @Test
  public void getListOfDaysBetweenTwoWeeks() {
    LocalDate firstDayOfWeek = LocalDate.of(2016, Month.AUGUST, 22);
    LocalDate end = firstDayOfWeek.plusWeeks(1).plusDays(-1);

    List<LocalDate> expectedList = IntStream.range(22, 29)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .collect(Collectors.toList());

    List<LocalDate> listOfDaysBetweenTwoDates = DateHelper.getListOfDaysBetweenTwoDates(firstDayOfWeek, end);

    Assert.assertEquals(expectedList, listOfDaysBetweenTwoDates);
  }

  @Test
  public void getFirstDayOfWeek() {
    List<Date> actual = IntStream.range(1, 29)
        .mapToObj(day -> LocalDate.of(2016, Month.AUGUST, day))
        .map(DateHelper::getFirstDayOfWeek)
        .map(DateHelper::localDateToDate)
        .collect(Collectors.toList());

    List<Date> expected = IntStream.range(1, 29)
        .mapToObj(day -> new GregorianCalendar(2016, Calendar.AUGUST, day, 0, 0, 0))
        .map(Calendar::getTime)
        .map(DateHelper::getFirstDayOfWeek)
        .collect(Collectors.toList());

    Assert.assertEquals(expected, actual);
  }
}
