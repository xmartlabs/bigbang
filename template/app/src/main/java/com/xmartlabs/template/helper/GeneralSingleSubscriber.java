package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.xmartlabs.template.ui.common.TemplateView;

import java.lang.ref.WeakReference;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class GeneralSingleSubscriber<T> implements SingleObserver<T> {
  @NonNull
  private WeakReference<TemplateView> viewReference;

  public GeneralSingleSubscriber() {
    this(null);
  }

  public GeneralSingleSubscriber(@Nullable TemplateView templateView) {
    viewReference = new WeakReference<>(templateView);
  }

  @Override
  public void onSubscribe(@NonNull Disposable disposable) {}

  @Override
  public void onError(@NonNull Throwable throwable) {
    TemplateView view = viewReference.get();
    if (alertOnError(throwable) && view != null && view.isViewAlive()) {
      view.showError(throwable, getErrorMessage(throwable));
    }
  }

  @Override
  public void onSuccess(@NonNull T t) {}

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
