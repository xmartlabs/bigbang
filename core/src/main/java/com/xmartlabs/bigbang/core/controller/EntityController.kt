package com.xmartlabs.bigbang.core.controller

import android.support.annotation.CheckResult
import com.xmartlabs.bigbang.core.extensions.observeOnIo
import com.xmartlabs.bigbang.core.extensions.subscribeOnIo
import com.xmartlabs.bigbang.core.model.EntityWithId
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * [Controller] that provides all the helpers needed to perform CRUD operations with both the service and a local
 * database.
 * Clients of this class should extend it and expose the desired CRUD operations making use of the provided helpers.
 * @param <Id> the type of the primary key
 * *
 * @param <E> the entity to be manipulated
 * *
 * @param <Condition> to be able to filter the entities before returning them
 * *
 * @param <S> the [EntityServiceProvider] type
 */
abstract class EntityController<Id, E : EntityWithId<Id>, in Condition, out S : EntityServiceProvider<Id, E>,
    out DAO : EntityDao<Id, E, Condition>>(
    protected val entityDao: DAO,
    protected val entityServiceProvider: S
) : Controller() {

  /**
   * Retrieves the entities from the db that match the `conditions` and, when the service call returns,
   * creates/updates the entities in the db. Then, it emits all the service entities.
   *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @param conditions the set of [Condition] to filter the entities
   * *
   * @return a [Flowable] that will emit the local entities matching the conditions and the ones retrieved from
   * * the service call.
   */
  @CheckResult
  protected open fun getEntities(serviceCall: Single<List<E>>, vararg conditions: Condition) = Flowable
        .concatArrayDelayError(
            entityDao.getEntities(*conditions).toFlowable(),
            serviceCall.applyIoSchedulers().toFlowable())
        .subscribeOnIo()
        .observeOnIo(true)
        .scan { _, serviceEntities -> entityDao.deleteAndInsertEntities(serviceEntities, *conditions).blockingGet() }
        .applyIoSchedulers()

  /**
   * Retrieves the entity with the `id` provided from the local db if present and updates/creates the said entity
   * after retrieving it from service.
   *
   * *
   * @param id the primary key value of the entity
   * *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @return the entity with the `id`
   */
  @CheckResult
  protected open fun getEntity(id: Id, serviceCall: (Id) -> Single<E>) =
      Observable.mergeDelayError(entityDao.getEntity(id).toObservable(),
          getServiceEntity(id, serviceCall).toObservable())

  /**
   * Retrieves the entity from db and, only if not present, then it's retrieved from service.
   *
   * *
   * @param id the primary key value of the entity
   * *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @return the entity with the `id`
   */
  @CheckResult
  protected open fun getEntityFromDbIfPresentOrGetFromService(id: Id, serviceCall: (Id) -> Single<E>) =
      entityDao.getEntity(id)
          .switchIfEmpty(getServiceEntity(id, serviceCall).toMaybe()).toSingle()

  /**
   * Retrieves the entity whose primary key value equals `id` from the service.
   * *
   * @param id the primary key value of the entity
   * *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @return the entity with the `id`
   */
  @CheckResult
  protected open fun getServiceEntity(id: Id, serviceCall: (Id) -> Single<E>) =
      serviceCall(id).applyIoSchedulers()
          .doOnSuccess { entityDao.createEntity(it) }

  /**
   * Retrieves the entity whose primary key value equals `id` from the service and stores the result in db.
   *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @return the entity with the `id`
   */
  @CheckResult
  protected open fun createEntityAndGetValue(serviceCall: Single<E>) =
      serviceCall.applyIoSchedulers()
          .flatMap { entityDao.createEntity(it) }

  /**
   * Stores the entity retrieved from service whose primary key value equals `id` into the db.
   *
   * @param serviceCall the [Single] that makes the service call
   * *
   * @return a [Completable] that indicates whether or not the entity could be created
   */
  @CheckResult
  protected open fun createEntity(serviceCall: Single<E>) = createEntityAndGetValue(serviceCall).toCompletable()

  /**
   * Retrieves the entity from the service and updates it locally.
   *
   * @param entity the entity to update
   * *
   * @param serviceCall the function that makes the service call
   * *
   * @return the updated entity
   */
  @CheckResult
  protected open fun updateEntityAndGetValue(entity: E, serviceCall: (Id, E) -> Completable) =
      updateEntityAndGetValue(entity, serviceCall(entity.id, entity))

  /**
   * Updates the local entity with the values from `entity` if the `completable` completes successfully.
   *
   * @param entity the entity to update
   * *
   * @param completable the [Completable] that, if successful, will trigger the update
   * *
   * @return the updated entity
   */
  @CheckResult
  protected open fun updateEntityAndGetValue(entity: E, completable: Completable) =
      completable.applyIoSchedulers()
          .toSingleDefault(entity)
          .flatMap(entityDao::updateEntity)

  /**
   * Updates the `entity` with the contents from the service call.
   *
   * @param entity the entity to update
   * *
   * @param serviceCall the function that makes the service call
   * *
   * @return a [Completable] that indicates whether or not the entity could be updated
   */
  @CheckResult
  protected open fun updateEntity(entity: E, serviceCall: (Id, E) -> Completable) =
      updateEntityAndGetValue(entity, serviceCall).toCompletable()

  /**
   * Makes the service call to delete the entity and also deletes it locally.
   * *
   * @param entityId the value of the primary key of the entity to be deleted
   * *
   * @param serviceCall the function that makes the service call
   * *
   * @return a [Completable] that indicates whether or not the entity could be deleted
   */
  @CheckResult
  protected open fun deleteEntity(entityId: Id, serviceCall: (Id) -> Completable) =
      serviceCall(entityId).applyIoSchedulers()
          .concatWith(entityDao.deleteEntityWithId(entityId))

  /**
   * Makes the service call to delete the entity and also deletes it locally.
   *
   * @param entity the entity to be deleted
   * *
   * @param serviceCall the function that makes the service call
   * *
   * @return a [Completable] that indicates whether or not the entity could be deleted
   */
  @CheckResult
  protected open fun deleteEntity(entity: E, serviceCall: (Id) -> Completable) =
      serviceCall(entity.id).applyIoSchedulers()
          .concatWith(entityDao.deleteEntity(entity))
}
