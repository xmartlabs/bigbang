package com.xmartlabs.template.common.rx;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;

/**
 * Created by diegomedina24 on 21/3/17.
 */
@RequiredArgsConstructor
public final class CompletableObserverWithErrorHandling implements CompletableObserver {
  private final CompletableObserver completableObserver;
  private final Consumer<? super Throwable> onErrorCallback;

  @Override
  public void onSubscribe(Disposable d) {
    completableObserver.onSubscribe(d);
  }

  @Override
  public void onComplete() {
    completableObserver.onComplete();
  }

  @Override
  public void onError(Throwable e) {
    try {
      onErrorCallback.accept(e);
    } catch (Exception exception) {
      completableObserver.onError(exception);
    }
    completableObserver.onError(e);
  }
}
