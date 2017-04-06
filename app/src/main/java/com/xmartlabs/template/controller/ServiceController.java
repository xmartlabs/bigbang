package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.xmartlabs.template.common.exeption.ServiceExceptionWithMessage;
import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

public class ServiceController<T, D extends EntityWithId<T>> extends Controller
    implements ServiceProvider<T, D> {
  @CheckResult
  @NonNull
  @Override
  public <S> Single<S> makeServiceCall(Single<S> serviceCall) {
    return makeIoCall(serviceCall);
  }

  @CheckResult
  @NonNull
  @Override
  public Completable makeServiceCall(Completable serviceCall) {
    return makeIoCall(serviceCall);
  }

  @CheckResult
  @NonNull
  @Override
  public <S> Single<S> makeServiceCallAndGetResponse(Single<Response<S>> serviceCall) {
    return makeIoCall(serviceCall)
        .map(response -> {
          if (response.isSuccessful()) {
            return response.body();
          } else {
            throw new ServiceExceptionWithMessage(response);
          }
        });
  }

  @CheckResult
  @NonNull
  @Override
  public Single<List<D>> getEntities(@NonNull Single<List<D>> serviceCall) {
    return makeIoCall(serviceCall);
  }

  @CheckResult
  @NonNull
  @Override
  public Single<D> getEntity(@NonNull Single<D> serviceCall) {
    return makeServiceCall(serviceCall);
  }

  @CheckResult
  @NonNull
  @Override
  public Maybe<D> getEntityFromList(@NonNull Single<List<D>> serviceCall, T id) {
    return getEntities(serviceCall)
        .toObservable()
        .flatMap(Observable::fromIterable)
        .filter(entity -> Objects.equals(entity, id))
        .firstElement();
  }
}
