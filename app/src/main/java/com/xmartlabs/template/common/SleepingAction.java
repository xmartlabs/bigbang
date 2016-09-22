package com.xmartlabs.template.common;

import rx.Scheduler;
import rx.functions.Action0;

/**
 * Created by medina on 22/09/2016.
 * Copied from rx.internal.schedulers.SleepingAction
 */
class SleepingAction implements Action0 {
  private final Action0 underlying;
  private final Scheduler.Worker innerScheduler;
  private final long execTime;

  public SleepingAction(Action0 underlying, Scheduler.Worker scheduler, long execTime) {
    this.underlying = underlying;
    this.innerScheduler = scheduler;
    this.execTime = execTime;
  }

  @Override
  public void call() {
    if (innerScheduler.isUnsubscribed()) {
      return;
    }

    long delay = execTime - innerScheduler.now();
    if (delay > 0) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }

    // after waking up check the subscription
    if (innerScheduler.isUnsubscribed()) {
      return;
    }
    underlying.call();
  }
}
