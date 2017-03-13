package com.xmartlabs.template.common.rx;

import android.support.annotation.NonNull;

import lombok.RequiredArgsConstructor;
import rx.Completable;
import rx.CompletableSubscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by mirland on 10/02/17.
 */
@RequiredArgsConstructor
public class OnCompletableSubscribeWithErrorHandle implements Completable.OnSubscribe {
  @NonNull
  private final Completable.OnSubscribe source;
  @NonNull
  private final Action1<Throwable> onErrorHandleCallback;

  @Override
  public void call(CompletableSubscriber subscriber) {
    source.call(new OnErrorSubscriber(subscriber, onErrorHandleCallback));
  }

  @RequiredArgsConstructor
  private static final class OnErrorSubscriber implements CompletableSubscriber {
    private final CompletableSubscriber actual;
    private final Action1<Throwable> onErrorHandleCallback;

    @Override
    public void onCompleted() {
      actual.onCompleted();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
      actual.onSubscribe(subscription);
    }

    @Override
    public void onError(Throwable e) {
      onErrorHandleCallback.call(e);
      actual.onError(e);
    }
  }
}
