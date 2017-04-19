package com.xmartlabs.base.core.rx.error;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * Implementation of {@link Observer} that calls a {@link Consumer} function {@code onError}.
 * To be used as an Observer hook with RxJavaPlugins.
 */
@RequiredArgsConstructor
public final class ObserverWithErrorHandling<T> implements Observer<T> {
  private final Observer<T> observer;
  private final Consumer<? super Throwable> onErrorCallback;

  @Override
  public void onSubscribe(Disposable d) {
    observer.onSubscribe(d);
  }

  @Override
  public void onNext(T t) {
    observer.onNext(t);
  }

  @Override
  public void onError(Throwable e) {
    try {
      onErrorCallback.accept(e);
    } catch (Exception exception) {
      Timber.e(exception);
    }
    observer.onError(e);
  }

  @Override
  public void onComplete() {
    observer.onComplete();
  }
}
