package com.xmartlabs.template.module;

import org.threeten.bp.Clock;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

import java.util.TimeZone;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by medina on 22/09/2016.
 */
@Module
public class MockClockModule {
  private static final String DEFAULT_TIME_ZONE_STRING = "GMT-03:00";
  public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING);

  @Provides
  @Singleton
  public Clock provideClock() {
    Instant instant = Instant.parse("2016-05-06T10:15:30.00Z");
    ZoneId zoneId = ZoneId.of(DEFAULT_TIME_ZONE_STRING);
    return Clock.fixed(instant, zoneId);
  }
}
