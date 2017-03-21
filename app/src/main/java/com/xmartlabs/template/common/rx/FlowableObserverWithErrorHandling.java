package com.xmartlabs.template.common.rx;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;

/**
 * Created by diegomedina24 on 21/3/17.
 */
@RequiredArgsConstructor
public final class FlowableObserverWithErrorHandling<T> implements Subscriber<T> {
  private final Subscriber<T> subscriber;
  private final Consumer<? super Throwable> onErrorCallback;

  @Override
  public void onSubscribe(Subscription s) {
    subscriber.onSubscribe(s);
  }

  @Override
  public void onNext(T t) {
    subscriber.onNext(t);
  }

  @Override
  public void onError(Throwable t) {
    try {
      onErrorCallback.accept(t);
    } catch (Exception exception) {
      subscriber.onError(exception);
    }
    subscriber.onError(t);
  }

  @Override
  public void onComplete() {
    subscriber.onComplete();
  }
}
