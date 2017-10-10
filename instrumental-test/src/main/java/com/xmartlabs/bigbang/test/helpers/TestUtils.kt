package com.xmartlabs.bigbang.test.helpers

import org.threeten.bp.Duration
import timber.log.Timber

class TestUtils {
  companion object {
    @JvmStatic
    fun sleep(duration: Duration = Duration.ofSeconds(1)) {
      try {
        Thread.sleep(duration.toMillis())
      } catch (e: InterruptedException) {
        Timber.e(e)
      }
    }
  }
}