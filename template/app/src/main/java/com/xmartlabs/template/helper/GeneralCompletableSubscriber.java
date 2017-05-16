package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.xmartlabs.template.ui.common.TemplateView;

import java.lang.ref.WeakReference;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class GeneralCompletableSubscriber implements CompletableObserver {
  @NonNull
  private WeakReference<TemplateView> viewReference;

  public GeneralCompletableSubscriber() {
    this(null);
  }

  public GeneralCompletableSubscriber(@Nullable TemplateView templateView) {
    viewReference = new WeakReference<>(templateView);
  }

  @Override
  public void onSubscribe(@NonNull Disposable disposable) {}

  @Override
  public void onComplete() {}

  @Override
  public void onError(@NonNull Throwable throwable) {
    TemplateView view = viewReference.get();
    if (alertOnError(throwable) && view != null && view.isViewAlive()) {
      view.showError(throwable, getErrorMessage(throwable));
    }
  }

  @Nullable
  @StringRes
  protected Integer getErrorMessage(Throwable throwable) {
    return null;
  }

  @CheckResult
  protected boolean alertOnError(Throwable throwable) {
    return true;
  }
}
