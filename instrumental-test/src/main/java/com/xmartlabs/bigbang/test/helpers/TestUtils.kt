package com.xmartlabs.bigbang.test.helpers

import org.threeten.bp.Duration
import timber.log.Timber

/**
 * Helper class that contains useful generic operations for testing
 */
class TestUtils {
  companion object {
    /**
     * Causes the currently executing thread to cease execution for the specified `duration`
     *
     * @param duration the length of time to sleep
     */
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
