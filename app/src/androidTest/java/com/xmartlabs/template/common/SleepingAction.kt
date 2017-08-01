package com.xmartlabs.template.common

import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit

/** Copied from rx.internal.schedulers.SleepingAction.  */
internal class SleepingAction(
    private val innerScheduler: Scheduler.Worker,
    private val execTime: Long,
    private val unit: TimeUnit,
    private val underlying: () -> Unit
) : Runnable {

  override fun run() {
    val delay = execTime - innerScheduler.now(unit)
    if (delay == 0L) {
      return
    }

    try {
      Thread.sleep(delay)
      underlying()
    } catch (e: Exception) {
      Thread.currentThread().interrupt()
      throw RuntimeException(e)
    }
  }
}
