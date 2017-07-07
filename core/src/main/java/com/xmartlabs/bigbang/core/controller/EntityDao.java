package com.xmartlabs.bigbang.core.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface EntityDao<Id, E, Condition> {
  /**
   * Retrieves the entities that match the set of given {@code conditions}.
   *
   * @param conditions to filter the entities to be returned
   * @return the entities that satisfy the {@code conditions}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<E>> getEntities(@NonNull Condition... conditions);

  /**
   * Deletes the entities that satisfy all the {@code conditions} and then inserts them from {@code newEntities}.
   *
   * @param newEntities the entities to update (delete then insert)
   * @param conditions to filter the entities to be deleted
   * @return the inserted entities that satisfy the {@code conditions}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  Single<List<E>> deleteAndInsertEntities(@Nullable List<E> newEntities, @NonNull Condition... conditions);

  /**
   * Retrieves the entity with the given {@code id}, if exists.
   *
   * @param id the primary key value
   * @return the entity, if exists
   */
  @CheckResult
  @NonNull
  Maybe<E> getEntity(@NonNull Id id);

  /**
   * Retrieves the entity that satisfies the conditions, if any
   * If more than one entity satisfy the conditions, the first one will be returned
   *
   * @param conditions to find the entity
   * @return
   */
  @CheckResult
  @NonNull
  Maybe<E> getEntity(@NonNull Condition... conditions);

  /**
   * Creates the {@code entity} and returns it.
   *
   * @param entity the entity to be created
   * @return the created entity
   */
  @CheckResult
  @NonNull
  Single<E> createEntity(@NonNull E entity);

  /**
   * Updates the {@code entity}, or creates it if it doesn't exist.
   *
   * @param entity the entity to be updated
   * @return the updated entity
   */
  @CheckResult
  @NonNull
  Single<E> updateEntity(@NonNull E entity);

  /**
   * Deletes the entity with the given {@code entityId}.
   *
   * @param entityId the value of the primary key
   * @return a {@link Completable} indicating whether or not the entity was deleted
   */
  @CheckResult
  @NonNull
  Completable deleteEntityWithId(@NonNull Id entityId);

  /**
   * Deletes the {@code entity}.
   *
   * @param entity the entity to be deleted
   * @return a {@link Completable} indicating whether or not the entity was deleted
   */
  @CheckResult
  @NonNull
  Completable deleteEntity(@NonNull E entity);
}
