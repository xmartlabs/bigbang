package com.xmartlabs.bigbang.core.extensions

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/** Modifies an ObservableSource to perform its emissions and notifications on the IO thread */
fun <T : Any> Observable<T>.observeOnIo(delayError: Boolean = false) = this.observeOn(Schedulers.io(), delayError)
/** Modifies the Single to perform its emissions and notifications on the IO thread */
fun <T : Any> Single<T>.observeOnIo() = this.observeOn(Schedulers.io())
/** Wraps a Maybe to emit its item (or notify of its error) on the IO thread */
fun <T : Any> Maybe<T>.observeOnIo() = this.observeOn(Schedulers.io())
/** Modifies a Publisher to perform its emissions and notifications on the IO thread */
fun <T : Any> Flowable<T>.observeOnIo(delayError: Boolean = false) = this.observeOn(Schedulers.io(), delayError)
/** Returns a Completable which emits the terminal events from the thread of the IO thread */
fun Completable.observeOnIo() = this.observeOn(Schedulers.io())

/** Modifies an ObservableSource to perform its emissions and notifications on the Android main thread */
fun <T : Any> Observable<T>.observeOnMain(delayError: Boolean = false) = this.observeOn(AndroidSchedulers.mainThread(), delayError)
/** Modifies the Single to perform its emissions and notifications on the Android main thread */
fun <T : Any> Single<T>.observeOnMain() = this.observeOn(AndroidSchedulers.mainThread())
/** Wraps a Maybe to emit its item (or notify of its error) on the Android main thread */
fun <T : Any> Maybe<T>.observeOnMain() = this.observeOn(AndroidSchedulers.mainThread())
/** Modifies a Publisher to perform its emissions and notifications on the Android main thread */
fun <T : Any> Flowable<T>.observeOnMain(delayError: Boolean = false) = this.observeOn(AndroidSchedulers.mainThread(), delayError)
/** Returns a Completable which emits the terminal events from the thread of the Android main thread */
fun Completable.observeOnMain() = this.observeOn(AndroidSchedulers.mainThread())

/** Asynchronously subscribes Observers to this ObservableSource on the IO thread */
fun <T : Any> Observable<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())
/** Asynchronously subscribes subscribers to this Single on the IO thread */
fun <T : Any> Single<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())
/** Asynchronously subscribes subscribers to this Maybe on the IO thread */
fun <T : Any> Maybe<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())
/** Asynchronously subscribes Subscribers to this Publisher on the IO thread */
fun <T : Any> Flowable<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())
/** Returns a Completable which subscribes the child subscriber on the IO thread */
fun Completable.subscribeOnIo() = this.subscribeOn(Schedulers.io())

/** Asynchronously subscribes Observers to this ObservableSource on the Android main thread */
fun <T : Any> Observable<T>.subscribeOnMain() = this.subscribeOn(AndroidSchedulers.mainThread())
/** Asynchronously subscribes subscribers to this Single on the Android main thread */
fun <T : Any> Single<T>.subscribeOnMain() = this.subscribeOn(AndroidSchedulers.mainThread())
/** Asynchronously subscribes subscribers to this Maybe on the Android main thread */
fun <T : Any> Maybe<T>.subscribeOnMain() = this.subscribeOn(AndroidSchedulers.mainThread())
/** Asynchronously subscribes Subscribers to this Publisher on the Android main thread */
fun <T : Any> Flowable<T>.subscribeOnMain() = this.subscribeOn(AndroidSchedulers.mainThread())
/** Returns a Completable which subscribes the child subscriber on the Android main thread */
fun Completable.subscribeOnMain() = this.subscribeOn(AndroidSchedulers.mainThread())
