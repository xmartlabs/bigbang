package com.xmartlabs.template.controller;

import com.xmartlabs.template.BaseProjectApplication;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class Controller {
  public Controller() {
    BaseProjectApplication.getContext().inject(this);
  }

  protected <T> Single<T> makeIoCall(Single<T> serviceCall) {
    return serviceCall
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  protected <T> Observable<T> makeIoCall(Observable<T> serviceCall) {
    return serviceCall
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


  protected Completable makeIoCall(Completable serviceCall) {
    return serviceCall
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
