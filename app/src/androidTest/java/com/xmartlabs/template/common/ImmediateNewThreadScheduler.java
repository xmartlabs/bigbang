package com.xmartlabs.template.common;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.BooleanSubscription;

public class ImmediateNewThreadScheduler extends Scheduler {
  @Override
  public Scheduler.Worker createWorker() {
    return new ImmediateNewThreadWorker();
  }

  // Inspired from ImmediateScheduler.InnerImmediateScheduler
  private static class ImmediateNewThreadWorker extends Worker {
    final BooleanSubscription innerSubscription = new BooleanSubscription();

    @Override
    public Disposable schedule(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
      // since we are executing immediately on this thread we must cause this thread to sleep
      long execTime = ImmediateNewThreadWorker.this.now(unit) + unit.toMillis(delay);

      return schedule(new SleepingAction(run::run, this, execTime, unit));
    }

    @Override
    public void dispose() {
      innerSubscription.cancel();
    }

    @Override
    public boolean isDisposed() {
      return innerSubscription.isCancelled();
    }
  }
}

