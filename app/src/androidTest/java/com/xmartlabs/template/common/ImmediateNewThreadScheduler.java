package com.xmartlabs.template.common;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

/**
 * Created by medina on 22/09/2016.
 */
public class ImmediateNewThreadScheduler extends Scheduler {
  @Override
  public Scheduler.Worker createWorker() {
    return new ImmediateNewThreadWorker();
  }

  // Inspired from ImmediateScheduler.InnerImmediateScheduler
  private static class ImmediateNewThreadWorker extends Worker {
    final BooleanSubscription innerSubscription = new BooleanSubscription();

    @Override
    public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
      // since we are executing immediately on this thread we must cause this thread to sleep
      long execTime = ImmediateNewThreadWorker.this.now() + unit.toMillis(delayTime);

      return schedule(new SleepingAction(action, this, execTime));
    }

    @Override
    public Subscription schedule(Action0 action) {
      Thread thread = new Thread(action::call);
      thread.start();
      try {
        thread.join();
      } catch (InterruptedException e) {
        Timber.e(e);
      }
      return Subscriptions.unsubscribed();
    }

    @Override
    public void unsubscribe() {
      innerSubscription.unsubscribe();
    }

    @Override
    public boolean isUnsubscribed() {
      return innerSubscription.isUnsubscribed();
    }
  }
}

