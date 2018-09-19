package com.xmartlabs.bigbang.core.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.helper.SchedulersTransformationHelper;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
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
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link RxTransformerHelper#completableIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
   *
   * CompletableTransformer that subscribes the stream in the Io {@link Schedulers} and observes
   * it on the {Android main thread.
   *
   */
  @Deprecated
  @NonNull
  public static final CompletableTransformer completableIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link RxTransformerHelper#flowableIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
   *
   * SingleTransformer that subscribes the stream in the Io {@link Schedulers} and observes
   * it on the {Android main thread.
   *
   */
  @Deprecated
  @NonNull
  public static final FlowableTransformer flowableIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link RxTransformerHelper#maybeIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
   *
   * SingleTransformer that subscribes the stream in the Io {@link Schedulers} and observes
   * it on the {Android main thread.
   *
   */
  @Deprecated
  @NonNull
  public static final MaybeTransformer maybeIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link RxTransformerHelper#observableIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
   *
   * SingleTransformer that subscribes the stream in the Io {@link Schedulers} and observes
   * it on the {Android main thread.
   *
   */
  @Deprecated
  @NonNull
  public static final ObservableTransformer observableIoAndMainThreadTransformer = observable -> observable
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link RxTransformerHelper#singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
   *
   * SingleTransformer that subscribes the stream in the Io {@link Schedulers} and observes
   * it on the {Android main thread.
   *
   */
  @Deprecated
  @NonNull
  public static final SingleTransformer singleIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   * @deprecated The transformation shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link SchedulersTransformationHelper#applyCompletableIoSchedulersTransformation()}
   * (it subscribes and observes on Io bound {@link Schedulers})
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
    return completableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated The transformation shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link SchedulersTransformationHelper#applyFlowableIoSchedulersTransformation()}
   * (it subscribes and observes on Io bound {@link Schedulers})
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
    return (FlowableTransformer<T, T>) flowableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated The transformation shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link SchedulersTransformationHelper#applySingleIoSchedulersTransformation()}
   * (it subscribes and observes on Io bound {@link Schedulers})
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
    return (MaybeTransformer<T, T>) maybeIoAndMainThreadTransformer;
  }

  /**
   * @deprecated The transformation shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link SchedulersTransformationHelper#applySingleIoSchedulersTransformation()}
   * (it subscribes and observes on Io bound {@link Schedulers})
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
    return (ObservableTransformer<T, T>) observableIoAndMainThreadTransformer;
  }

  /**
   * @deprecated The transformation shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link SchedulersTransformationHelper#applySingleIoSchedulersTransformation()}
   * (it subscribes and observes on Io bound {@link Schedulers})
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
    return (SingleTransformer<T, T>) singleIoAndMainThreadTransformer;
  }
}
