package com.xmartlabs.bigbang.core.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.helper.RxTransformerHelper;

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
import io.reactivex.schedulers.Schedulers;

/**
 * Contains general controller methods.
 * It automatically injects inherited classes.
 */
public abstract class Controller {
  protected Controller() {
    Injector.inject(this);
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected CompletableTransformer applyCompletableIoSchedulersTransformation() {
    return RxTransformerHelper.completableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> FlowableTransformer<T, T> applyFlowableIoSchedulersTransformation() {
    //noinspection unchecked
    return (FlowableTransformer<T, T>) RxTransformerHelper.flowableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> MaybeTransformer<T, T> applyMaybeIoSchedulersTransformation() {
    //noinspection unchecked
    return (MaybeTransformer<T, T>) RxTransformerHelper.maybeIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> ObservableTransformer<T, T> applyObservableIoSchedulersTransformation() {
    //noinspection unchecked
    return (ObservableTransformer<T, T>) RxTransformerHelper.observableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  protected <T> SingleTransformer<T, T> applySingleIoSchedulersTransformation() {
    //noinspection unchecked
    return (SingleTransformer<T, T>) RxTransformerHelper.singleIoTransformer;
  }

  /**
   * @deprecated
   *
   * Provides the Io schedule {@link Completable} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @Deprecated
  @NonNull
  protected CompletableTransformer applyCompletableIoSchedulers() {
    return RxTransformerHelper.completableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated
   *
   * Provides the Io schedule {@link Observable} transformation.
   * Subscribes the stream to Io bound {@link Flowable} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @Deprecated
  @NonNull
  protected <T> FlowableTransformer<T, T> applyFlowableIoSchedulers() {
    //noinspection unchecked
    return (FlowableTransformer<T, T>) RxTransformerHelper.flowableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated
   *
   * Provides the Io schedule {@link Maybe} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @Deprecated
  @NonNull
  protected <T> MaybeTransformer<T, T> applyMaybeIoSchedulers() {
    //noinspection unchecked
    return (MaybeTransformer<T, T>) RxTransformerHelper.maybeIoAndMainThreadTransformer;
  }

  /**
   * @deprecated
   *
   * Provides the Io schedule {@link Observable} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @Deprecated
  @NonNull
  protected <T> ObservableTransformer<T, T> applyObservableIoSchedulers() {
    //noinspection unchecked
    return (ObservableTransformer<T, T>) RxTransformerHelper.observableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated
   *
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes the stream to Io bound {@link Schedulers} and observes it in the {Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @Deprecated
  @NonNull
  protected <T> SingleTransformer<T, T> applySingleIoSchedulers() {
    //noinspection unchecked
    return (SingleTransformer<T, T>) RxTransformerHelper.singleIoAndMainThreadTransformer;
  }
}
