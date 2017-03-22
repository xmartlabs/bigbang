package com.xmartlabs.template.common.rx;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;

/**
 * Created by diegomedina24 on 21/3/17.
 */
@RequiredArgsConstructor
public final class SingleObserverWithErrorHandling<T> implements SingleObserver<T> {
  private final SingleObserver<T> source;
  private final Consumer<? super Throwable> onErrorCallback;

  @Override
  public void onSubscribe(Disposable d) {
    source.onSubscribe(d);
  }

  @Override
  public void onSuccess(T t) {
    source.onSuccess(t);
  }

  @Override
  public void onError(Throwable e) {
    try {
      onErrorCallback.accept(e);
    } catch (Exception exception) {
      Timber.e(exception);
    }
    source.onError(e);
  }
}
