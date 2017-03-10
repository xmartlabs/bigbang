package com.xmartlabs.template.common.rx;

import lombok.RequiredArgsConstructor;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;

/**
 * Created by mirland on 10/02/17.
 */
@RequiredArgsConstructor
public class OnSingleSubscribeWithErrorHandle<T> implements Single.OnSubscribe<T> {
  private final Single.OnSubscribe<T> source;
  private final Action1<Throwable> onErrorHandleCallback;

  @Override
  public void call(SingleSubscriber<? super T> singleSubscriber) {
    source.call(new OnErrorSubscriber<>(singleSubscriber, onErrorHandleCallback));
  }

  private static final class OnErrorSubscriber<T> extends SingleSubscriber<T> {
    private final SingleSubscriber<? super T> actual;
    private final Action1<Throwable> onErrorHandleCallback;

    OnErrorSubscriber(SingleSubscriber<? super T> actual, Action1<Throwable> onErrorHandleCallback) {
      this.actual = actual;
      this.onErrorHandleCallback = onErrorHandleCallback;
      actual.add(this);
    }

    @Override
    public void onSuccess(T t) {
      actual.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
      onErrorHandleCallback.call(e);
      actual.onError(e);
    }
  }
}
