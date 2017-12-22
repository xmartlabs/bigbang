package com.xmartlabs.bigbang.core.controller

import android.support.annotation.CheckResult
import com.xmartlabs.bigbang.core.Injector
import com.xmartlabs.bigbang.core.extensions.observeOnIo
import com.xmartlabs.bigbang.core.extensions.subscribeOnIo
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Maybe
import io.reactivex.MaybeTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

/**
 * Contains general controller methods.
 * It automatically injects inherited classes.
 */
@Suppress("UNCHECKED_CAST")
abstract class Controller protected constructor() {
  protected val completableIoTransformer = CompletableTransformer { it.subscribeOnIo().observeOnIo() }
  protected val flowableIoTransformer = FlowableTransformer<Any, Any> { it.subscribeOnIo().observeOnIo() }
  protected val maybeIoTransformer = MaybeTransformer<Any, Any> { it.subscribeOnIo().observeOnIo() }
  protected val observableIoTransformer = ObservableTransformer<Any, Any> { it.subscribeOnIo().observeOnIo() }
  protected val singleIoTransformer = SingleTransformer<Any, Any> { it.subscribeOnIo().observeOnIo() }

  init {
    Injector.inject(this)
  }

  /**
   * Provides the Io schedule [Completable] transformation.
   * Subscribes the stream to Io bound [Schedulers] and observes it in the Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  protected open fun Completable.applyIoSchedulers() = this.compose(completableIoTransformer)

  /**
   * Provides the Io schedule [Observable] transformation.
   * Subscribes the stream to Io bound [Flowable] and observes it in the Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  protected open fun <T : Any> Flowable<T>.applyIoSchedulers() = this.compose(flowableIoTransformer as FlowableTransformer<T, T>)

  /**
   * Provides the Io schedule [Maybe] transformation.
   * Subscribes the stream to Io bound [Schedulers] and observes it in the Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  protected open fun <T : Any> Maybe<T>.applyIoSchedulers() = this.compose(maybeIoTransformer as MaybeTransformer<T, T>)

  /**
   * Provides the Io schedule [Observable] transformation.
   * Subscribes the stream to Io bound [Schedulers] and observes it in the Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  protected open fun <T : Any> Observable<T>.applyIoSchedulers() = this.compose(observableIoTransformer as ObservableTransformer<T, T>)

  /**
   * Provides the Io schedule [Single] transformation.
   * Subscribes the stream to Io bound [Schedulers] and observes it in the Android main thread.
   *
   * @return The stream with the schedule transformation
   */
  @CheckResult
  protected open fun <T : Any> Single<T>.applyIoSchedulers() = this.compose(singleIoTransformer as SingleTransformer<T, T>)
}
