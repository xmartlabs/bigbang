package com.xmartlabs.template.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.xmartlabs.template.ui.common.TemplateView;

import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import io.reactivex.FlowableSubscriber;

public class GeneralFlowableSubscriber<T> implements FlowableSubscriber<T> {
  @NonNull
  private WeakReference<TemplateView> viewReference;

  public GeneralFlowableSubscriber() {
    this(null);
  }

  public GeneralFlowableSubscriber(@Nullable TemplateView templateView) {
    viewReference = new WeakReference<>(templateView);
  }

  @Override
  public void onSubscribe(@NonNull Subscription subscription) {
    subscription.request(getMaxNumberOfElements());
  }

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

  protected long getMaxNumberOfElements() {
    return Long.MAX_VALUE;
  }
}
