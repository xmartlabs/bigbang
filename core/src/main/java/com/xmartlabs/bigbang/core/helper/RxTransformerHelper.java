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
 * Created by bruno on 1/12/18.
 */
public class RxTransformerHelper {
  /**
   *
   */
  @NonNull
  public static final CompletableTransformer completableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   *
   */
  @NonNull
  public static final FlowableTransformer flowableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   *
   */
  @NonNull
  public static final MaybeTransformer maybeIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   *
   */
  @NonNull
  public static final ObservableTransformer observableIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   *
   */
  @NonNull
  public static final SingleTransformer singleIoTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io());

  /**
   *
   */
  @Deprecated
  @NonNull
  public static final CompletableTransformer completableIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   *
   */
  @Deprecated
  @NonNull
  public static final FlowableTransformer flowableIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   *
   */
  @Deprecated
  @NonNull
  public static final MaybeTransformer maybeIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   *
   */
  @Deprecated
  @NonNull
  public static final ObservableTransformer observableIoAndMainThreadTransformer = observable -> observable
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

  /**
   *
   */
  @Deprecated
  @NonNull
  public static final SingleTransformer singleIoAndMainThreadTransformer = upstream -> upstream
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
}
