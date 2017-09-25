package com.xmartlabs.bigbang.test.helpers

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.threeten.bp.Clock
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime

fun sleep(duration: Duration = Duration.ofSeconds(1)) {
  try {
    Thread.sleep(duration.toMillis())
  } catch (e: InterruptedException) {
    e.printStackTrace()
  }
}

fun mockTime(currentMockedTime: ZonedDateTime) {
  val clock = mock<Clock>(Clock::class.java)
  `when`<Instant>(clock.instant()).thenReturn(currentMockedTime.toInstant())
}
