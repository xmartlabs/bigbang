package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface EntityDao<Id, E, Condition> {
  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<E>> getEntities(@NonNull Condition... conditions);

  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<E>> deleteAndInsertEntities(@Nullable List<E> newEntities, @NonNull Condition... conditions);

  @CheckResult
  @NonNull
  Maybe<E> getEntity(@NonNull Id id);

  @CheckResult
  @NonNull
  Single<E> createEntity(@NonNull E entity);

  @CheckResult
  @NonNull
  Single<E> updateEntity(@NonNull E entity);

  @CheckResult
  @NonNull
  Completable deleteEntityWithId(@NonNull Id entityId);

  @CheckResult
  @NonNull
  Completable deleteEntity(@NonNull E entity);
}
