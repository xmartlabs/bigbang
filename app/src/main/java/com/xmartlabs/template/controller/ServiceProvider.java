package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

public interface ServiceProvider<T, D extends EntityWithId<T>> {
  @CheckResult
  @NonNull
  <S> Single<S> makeServiceCall(Single<S> serviceCall);

  @CheckResult
  @NonNull
  Completable makeServiceCall(Completable serviceCall);

  @CheckResult
  @NonNull
  <S> Single<S> makeServiceCallAndGetResponse(Single<Response<S>> serviceCall);

  @CheckResult
  @NonNull
  Single<List<D>> getEntities(@NonNull Single<List<D>> serviceCall);

  @CheckResult
  @NonNull
  Single<D> getEntity(@NonNull Single<D> serviceCall);

  @CheckResult
  @NonNull
  Maybe<D> getEntityFromList(@NonNull Single<List<D>> serviceCall, T id);
}
