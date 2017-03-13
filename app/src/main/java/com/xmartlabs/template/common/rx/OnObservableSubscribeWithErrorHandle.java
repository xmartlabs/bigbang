package com.xmartlabs.template.common.rx;

import lombok.RequiredArgsConstructor;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by mirland on 10/02/17.
 */
@RequiredArgsConstructor
public class OnObservableSubscribeWithErrorHandle<T> implements Observable.OnSubscribe<T> {
  private final Observable.OnSubscribe<T> source;
  private final Action1<Throwable> onErrorHandleCallback;

  @Override
  public void call(Subscriber<? super T> subscriber) {
    source.call(new OnErrorSubscriber<>(subscriber, onErrorHandleCallback));
  }

  private static final class OnErrorSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> actual;
    private final Action1<Throwable> onErrorHandleCallback;

    OnErrorSubscriber(Subscriber<? super T> actual, Action1<Throwable> onErrorHandleCallback) {
      super(actual);
      this.actual = actual;
      this.onErrorHandleCallback = onErrorHandleCallback;
    }

    @Override
    public void onCompleted() {
      actual.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
      onErrorHandleCallback.call(e);
      actual.onError(e);
    }

    @Override
    public void onNext(T t) {
      actual.onNext(t);
    }
  }
}
