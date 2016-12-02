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
}
