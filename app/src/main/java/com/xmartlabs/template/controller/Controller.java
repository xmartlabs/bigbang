package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.template.BaseProjectApplication;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Contains general controller methods.
 * It automatically injects inherited classes.
 */
public abstract class Controller {
  @NonNull
  private final CompletableTransformer completableIoTransformer = upstream -> upstream
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
  @NonNull
  private final ObservableTransformer observableIoTransformer = observable -> observable
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  @NonNull
  private final SingleTransformer singleIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  protected Controller() {
    BaseProjectApplication.getContext().inject(this);
  }

  /**
   * Provides the Io schedule {@link Completable} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected CompletableTransformer applyCompletableIoSchedulers() {
    return completableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Observable} transformation.
   * Subscribes the stream to Io bound {@link Flowable} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> FlowableTransformer<T, T> applyFlowableIoSchedulers() {
    //noinspection unchecked
    return (FlowableTransformer<T, T>) flowableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Maybe} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> MaybeTransformer<T, T> applyMaybeIoSchedulers() {
    //noinspection unchecked
    return (MaybeTransformer<T, T>) maybeIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Observable} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> ObservableTransformer<T, T> applyObservableIoSchedulers() {
    //noinspection unchecked
    return (ObservableTransformer<T, T>) observableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> SingleTransformer<T, T> applySingleIoSchedulers() {
    //noinspection unchecked
    return (SingleTransformer<T, T>) singleIoTransformer;
  }
}
