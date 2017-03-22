package com.xmartlabs.template.common;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * Created by medina on 22/09/2016.
 * Copied from rx.internal.schedulers.SleepingAction
 */
class SleepingAction implements Runnable {
  private final Action underlying;
  private final Scheduler.Worker innerScheduler;
  private final long execTime;
  private final TimeUnit unit;

  public SleepingAction(Action underlying, Scheduler.Worker scheduler, long execTime, TimeUnit unit) {
    this.underlying = underlying;
    this.innerScheduler = scheduler;
    this.execTime = execTime;
    this.unit = unit;
  }

  @Override
  public void run() {
    long delay = execTime - innerScheduler.now(unit);
    if (delay == 0) {
      return;
    }

    try {
      Thread.sleep(delay);
      underlying.run();
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
