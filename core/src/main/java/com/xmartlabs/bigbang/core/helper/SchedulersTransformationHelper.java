package com.xmartlabs.bigbang.core.helper;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

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
 * Contains the different Io Schedulers transformations for main RxJava operators.
 */
public class SchedulersTransformationHelper {
  /**
   * Provides the Io schedule {@link Completable} transformation.
   * Subscribes and observes the stream in the Io {@link Schedulers}.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  public static CompletableTransformer applyCompletableIoSchedulersTransformation() {
    return RxTransformerHelper.completableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Flowable} transformation.
   * Subscribes and observes the stream in the Io {@link Schedulers}.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  public static <T> FlowableTransformer<T, T> applyFlowableIoSchedulersTransformation() {
    //noinspection unchecked
    return (FlowableTransformer<T, T>) RxTransformerHelper.flowableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Maybe} transformation.
   * Subscribes and observes the stream in the Io {@link Schedulers}.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  public static <T> MaybeTransformer<T, T> applyMaybeIoSchedulersTransformation() {
    //noinspection unchecked
    return (MaybeTransformer<T, T>) RxTransformerHelper.maybeIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Observable} transformation.
   * Subscribes and observes the stream in the Io {@link Schedulers}.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  public static <T> ObservableTransformer<T, T> applyObservableIoSchedulersTransformation() {
    //noinspection unchecked
    return (ObservableTransformer<T, T>) RxTransformerHelper.observableIoTransformer;
  }

  /**
   * Provides the Io schedule {@link Single} transformation.
   * Subscribes and observes the stream on Io bound {@link Schedulers}.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  @NonNull
  public static <T> SingleTransformer<T, T> applySingleIoSchedulersTransformation() {
    //noinspection unchecked
    return (SingleTransformer<T, T>) RxTransformerHelper.singleIoTransformer;
  }
}
