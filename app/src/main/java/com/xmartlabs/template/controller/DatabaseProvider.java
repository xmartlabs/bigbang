package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface DatabaseProvider<Id, D, Condition> {
  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<D>> getEntities(@NonNull Condition... conditions);

  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<D>> updateEntities(List<D> newEntities, @NonNull Condition... conditions);

  @CheckResult
  @NonNull
  Maybe<D> getEntity(Id id);

  @CheckResult
  @NonNull
  Single<D> createEntity(@NonNull D entity);

  @CheckResult
  @NonNull
  Single<D> updateEntity(@NonNull D entity);

  @CheckResult
  @NonNull
  Completable deleteEntityFromId(Id entityId);

  @CheckResult
  @NonNull
  Completable deleteEntityFromEntity(D entity);
}
