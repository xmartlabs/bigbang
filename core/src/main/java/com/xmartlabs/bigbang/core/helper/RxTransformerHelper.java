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
  static final CompletableTransformer completableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * FlowableTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  static final FlowableTransformer flowableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * MaybeTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  static final MaybeTransformer maybeIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * ObservableTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  static final ObservableTransformer observableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   * SingleTransformer that subscribes and observes the stream on Io bound {@link Schedulers}.
   */
  @NonNull
  static final SingleTransformer singleIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());
}
