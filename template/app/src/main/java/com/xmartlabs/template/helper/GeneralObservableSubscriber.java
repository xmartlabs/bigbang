package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.xmartlabs.template.ui.common.TemplateView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

public class GeneralObservableSubscriber<T> implements Subscriber<T> {
  @NonNull
  private WeakReference<TemplateView> viewReference;
  
  public GeneralObservableSubscriber() {
    this(null);
  }

  public GeneralObservableSubscriber(@Nullable TemplateView templateView) {
    viewReference = new WeakReference<>(templateView);
  }

  @Override
  public void onSubscribe(Subscription subscription) {}

  @Override
  public void onNext(T t) {}

  @Override
  public void onError(Throwable throwable) {
    TemplateView view = viewReference.get();
    if (alertOnError(throwable) && view != null && view.isViewAlive()) {
      view.showError(throwable, getErrorMessage(throwable));
    }
  }

  @Override
  public void onComplete() {}

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
