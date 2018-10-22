package com.xmartlabs.bigbang.mvvm.result

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription

/** Transform a [Completable] into a [LiveData] of [Result]. */
fun Completable.toResult(): LiveData<Result<Nothing>> {
  val source = MutableLiveData<Result<Nothing>>()
  this
      .observeOn(Schedulers.io())
      .subscribe(object : CompletableObserver {
        override fun onSubscribe(disposable: Disposable) {
          source.value = Result.Loading
        }

        override fun onComplete() {
          source.value = Result.Success(null)
        }

        override fun onError(throwable: Throwable) {
          source.value = Result.Error(throwable)
        }
      })
  return source
}

/** Transform a [Single] into a [LiveData] of [Result]. */
fun <T> Single<T>.toResult(): LiveData<Result<T>> {
  val source = MutableLiveData<Result<T>>()
  this
      .observeOn(Schedulers.io())
      .subscribe(object : SingleObserver<T> {
        override fun onSubscribe(disposable: Disposable) {
          source.value = Result.Loading
        }

        override fun onSuccess(t: T) {
          source.value = Result.Success(null)
        }

        override fun onError(throwable: Throwable) {
          source.value = Result.Error(throwable)
        }
      })
  return source
}

/** Transform a [Observable] into a [LiveData] of [Result]. */
fun <T> Observable<T>.toResult(): LiveData<Result<T>> {
  val source = MutableLiveData<Result<T>>()
  this
      .observeOn(Schedulers.io())
      .subscribe(object : Observer<T> {
        override fun onSubscribe(d: Disposable) {
          source.value = Result.Loading
        }

        override fun onComplete() {}

        override fun onNext(t: T) {
          source.value = Result.Success(null)
        }

        override fun onError(throwable: Throwable) {
          source.value = Result.Error(throwable)
        }
      })
  return source
}

/** Transform a [Maybe] into a [LiveData] of [Result]. */
fun <T> Maybe<T>.toResult(): LiveData<Result<T>> {
  val source = MutableLiveData<Result<T>>()
  this
      .observeOn(Schedulers.io())
      .subscribe(object : MaybeObserver<T> {
        override fun onComplete() {}

        override fun onSubscribe(disposable: Disposable) {
          source.value = Result.Loading
        }

        override fun onSuccess(t: T) {
          source.value = Result.Success(null)
        }

        override fun onError(throwable: Throwable) {
          source.value = Result.Error(throwable)
        }
      })
  return source
}

/** Transform a [Flowable] into a [LiveData] of [Result]. */
fun <T> Flowable<T>.toResult(): LiveData<Result<T>> {
  val source = MutableLiveData<Result<T>>()
  this
      .observeOn(Schedulers.io())
      .subscribe(object : FlowableSubscriber<T> {
        override fun onNext(t: T) {
          source.value = Result.Success(null)
        }

        override fun onComplete() {}

        override fun onSubscribe(subscription: Subscription) {
          subscription.request(Long.MAX_VALUE)
          source.value = Result.Loading
        }

        override fun onError(throwable: Throwable) {
          source.value = Result.Error(throwable)
        }
      })
  return source
}
