package com.xmartlabs.bigbang.core.controller

import androidx.annotation.CheckResult

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface EntityDao<in Id, E, in Condition> {
  /**
   * Retrieves the entities that match the set of given `conditions`.
   *
   * @param conditions to filter the entities to be returned
   * *
   * @return the entities that satisfy the `conditions`
   */
  @CheckResult
  fun getEntities(vararg conditions: Condition): Single<List<E>>

  /**
   * Deletes the entities that satisfy all the `conditions` and then inserts them from `newEntities`.
   *
   * @param newEntities the entities to update (delete then insert)
   * *
   * @param conditions to filter the entities to be deleted
   * *
   * @return the inserted entities that satisfy the `conditions`
   */
  @CheckResult
  fun deleteAndInsertEntities(newEntities: List<E>, vararg conditions: Condition): Single<List<E>>

  /**
   * Retrieves the entity with the given `id`, if exists.
   *
   * @param id the primary key value
   * *
   * @return the entity, if exists
   */
  @CheckResult
  fun getEntity(id: Id): Maybe<E>

  /**
   * Retrieves the entity that satisfies the conditions, if any
   * If more than one entity satisfy the conditions, the first one will be returned
   *
   * @param conditions to find the entity
   * *
   * @return the entity, if exists
   */
  @CheckResult
  fun getEntity(vararg conditions: Condition): Maybe<E>

  /**
   * Creates the `entity` and returns it.
   *
   * @param entity the entity to be created
   * *
   * @return the created entity
   */
  @CheckResult
  fun createEntity(entity: E): Single<E>

  /**
   * Updates the `entity`, or creates it if it doesn't exist.
   *
   * @param entity the entity to be updated
   * *
   * @return the updated entity
   */
  @CheckResult
  fun updateEntity(entity: E): Single<E>

  /**
   * Deletes the entity with the given `entityId`.
   *
   * @param entityId the value of the primary key
   * *
   * @return a [Completable] indicating whether or not the entity was deleted
   */
  @CheckResult
  fun deleteEntityWithId(entityId: Id): Completable

  /**
   * Deletes the `entity`.
   *
   * @param entity the entity to be deleted
   * *
   * @return a [Completable] indicating whether or not the entity was deleted
   */
  @CheckResult
  fun deleteEntity(entity: E): Completable
}
