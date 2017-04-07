package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class Controller {
  @NonNull
  private final CompletableTransformer completableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  @NonNull
  private final ObservableTransformer observableIoTransformer =
      observable -> observable
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
  @NonNull
  private final SingleTransformer singleIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  @NonNull
  private final FlowableTransformer flowableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  @NonNull
  private final MaybeTransformer maybeIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  protected Controller() {
    BaseProjectApplication.getContext().inject(this);
  }

  @CheckResult
  @NonNull
  protected <T> ObservableTransformer<T, T> applyObservableIoSchedulers() {
    //noinspection unchecked
    return (ObservableTransformer<T, T>) observableIoTransformer;
  }

  @CheckResult
  @NonNull
  protected <T> SingleTransformer<T, T> applySingleIoSchedulers() {
    //noinspection unchecked
    return (SingleTransformer<T, T>) singleIoTransformer;
  }

  @CheckResult
  @NonNull
  protected <T> FlowableTransformer<T, T> applyFlowableIoSchedulers() {
    //noinspection unchecked
    return (FlowableTransformer<T, T>) flowableIoTransformer;
  }

  @CheckResult
  @NonNull
  protected <T> MaybeTransformer<T, T> applyMaybeIoSchedulers() {
    //noinspection unchecked
    return (MaybeTransformer<T, T>) maybeIoTransformer;
  }

  @CheckResult
  @NonNull
  protected CompletableTransformer applyCompletableIoSchedulers() {
    return completableIoTransformer;
  }
}
