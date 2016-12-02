package com.xmartlabs.template.common;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mirland on 16/03/16.
 */
public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {
  private final int maxRetries;
  private final long retryDelayMillis;
  private int retryCount = 0;

  public RetryWithDelay(final int maxRetries, long intervalTime, TimeUnit intervalUnit) {
    this.maxRetries = maxRetries;
    this.retryDelayMillis = TimeUnit.MILLISECONDS.convert(intervalTime, intervalUnit);
  }

  public RetryWithDelay(long intervalTime, TimeUnit intervalUnit) {
    this(-1, intervalTime, intervalUnit);
  }

  @Override
  public Observable<?> call(Observable<? extends Throwable> attempts) {
    return attempts
        .flatMap(new Func1<Throwable, Observable<?>>() {
          @Override
          public Observable<?> call(Throwable throwable) {
            if (shouldRetry(throwable) && (++retryCount < maxRetries || maxRetries < 0)) {
              return Observable.timer(retryDelayMillis,
                  TimeUnit.MILLISECONDS);
            }
            return Observable.error(throwable);
          }
        });
  }

  @SuppressWarnings("UnusedParameters")
  protected boolean shouldRetry(Throwable throwable) {
    return true;
  }
}