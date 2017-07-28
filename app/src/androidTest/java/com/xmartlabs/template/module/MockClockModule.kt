package com.xmartlabs.template.module

import dagger.Module
import dagger.Provides
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.util.*
import javax.inject.Singleton

@Module
class MockClockModule {
  companion object {
    private val DEFAULT_TIME_ZONE_STRING = "GMT-03:00"
    val DEFAULT_TIME_ZONE = TimeZone.getTimeZone(DEFAULT_TIME_ZONE_STRING)
  }
  
  @Provides
  @Singleton
  fun provideClock(): Clock {
    val instant = Instant.parse("2016-05-06T10:15:30.00Z")
    val zoneId = ZoneId.of(DEFAULT_TIME_ZONE_STRING)
    return Clock.fixed(instant, zoneId)
  }
}
