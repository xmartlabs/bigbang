package com.xmartlabs.template.common

import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.subscriptions.BooleanSubscription
import java.util.concurrent.TimeUnit

class ImmediateNewThreadScheduler : Scheduler() {
  override fun createWorker() = ImmediateNewThreadWorker()

  // Inspired from ImmediateScheduler.InnerImmediateScheduler
  class ImmediateNewThreadWorker : Scheduler.Worker() {
    internal val innerSubscription = BooleanSubscription()

    override fun schedule(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
      // since we are executing immediately on this thread we must cause this thread to sleep
      val execTime = this@ImmediateNewThreadWorker.now(unit) + unit.toMillis(delay)

      return schedule(SleepingAction(this, execTime, unit) { run.run() })
    }

    override fun dispose() = innerSubscription.cancel()

    override fun isDisposed() = innerSubscription.isCancelled
  }
}

