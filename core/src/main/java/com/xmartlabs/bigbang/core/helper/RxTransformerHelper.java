package com.xmartlabs.bigbang.core.helper;

import android.support.annotation.NonNull;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Contains RXJava's Transformers
 */
public class RxTransformerHelper {
  /**
   * CompletableTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  public static final CompletableTransformer completableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * FlowableTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  public static final FlowableTransformer flowableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * MaybeTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  public static final MaybeTransformer maybeIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * ObservableTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  public static final ObservableTransformer observableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * SingleTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  public static final SingleTransformer singleIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * @deprecated The transformer shouldn't observe on the {Android main thread. You should be the one
   * to decide when to observe on the main thread. Instead of this, use
   * {@link #singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
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
   * {@link #singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
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
   * {@link #singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
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
   * {@link #singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
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
   * {@link #singleIoTransformer} (it subscribes and observes on Io bound {@link Schedulers})
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
}
