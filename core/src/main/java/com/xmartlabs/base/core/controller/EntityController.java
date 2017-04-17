package com.xmartlabs.base.core.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.base.core.helper.function.BiFunction;
import com.xmartlabs.base.core.helper.function.Function;
import com.xmartlabs.base.core.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

/**
 * {@link Controller} that provides all the helpers needed to perform CRUD operations with both the service and a local
 * database.
 * Clients of this class should extend it and expose the desired CRUD operations making use of the provided helpers.
 *
 * @param <Id> the type of the primary key
 * @param <E> the entity to be manipulated
 * @param <Condition> to be able to filter the entities before returning them
 */
@RequiredArgsConstructor
public abstract class EntityController<Id, E extends EntityWithId<Id>, Condition> extends Controller {
  private final EntityDao<Id, E, Condition> entityDao;
  private final EntityServiceProvider<Id, E> entityServiceProvider;

  /**
   * Retrieves the entities from the db that match the {@code conditions} and, when the service call returns,
   * creates/updates the entities in the db. Then, it emits all the service entities.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @param conditions the set of {@link Condition} to filter the entities
   * @return a {@link Flowable} that will emit the local entities matching the conditions and the ones retrieved from
   * the service call.
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
  protected Flowable<List<E>> getEntities(@NonNull Single<List<E>> serviceCall, @NonNull Condition... conditions) {
    return Flowable
        .concatArrayDelayError(
            entityDao.getEntities(conditions).toFlowable(),
            serviceCall
                .compose(entityServiceProvider.applySingleServiceTransformation())
                .toFlowable()
        )
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .scan((databaseEntities, serviceEntities) ->
            entityDao.deleteAndInsertEntities(serviceEntities, conditions).blockingGet()
        )
        .compose(applyFlowableIoSchedulers());
  }

  /**
   * Retrieves the entity with the {@code id} provided from the local db if present and updates/creates the said entity
   * after retrieving it from service.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @param id the primary key value of the entity
   * @return the entity with the {@code id}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Observable<E> getEntity(@NonNull Function<Id, Single<E>> serviceCall, @NonNull Id id) {
    return Observable.mergeDelayError(
        entityDao.getEntity(id).toObservable(),
        getServiceEntity(serviceCall, id).toObservable()
    );
  }

  /**
   * Retrieves the entity from db and, only if not present, then it's retrieved from service.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @param id the primary key value of the entity
   * @return the entity with the {@code id}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> getEntityFromDbIfPresentOrGetFromService(@NonNull Function<Id, Single<E>> serviceCall,
                                                               @NonNull Id id) {
    return entityDao.getEntity(id)
        .switchIfEmpty(getServiceEntity(serviceCall, id).toMaybe())
        .toSingle();
  }

  /**
   * Retrieves the entity whose primary key value equals {@code id} from the service.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @param id the primary key value of the entity
   * @return the entity with the {@code id}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> getServiceEntity(@NonNull Function<Id, Single<E>> serviceCall, Id id) {
    return serviceCall.apply(id)
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .doOnSuccess(entityDao::updateEntity);
  }

  /**
   * Retrieves the entity whose primary key value equals {@code id} from the service and stores the result in db.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @return the entity with the {@code id}
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> createEntityAndGetValue(@NonNull Single<E> serviceCall) {
    return serviceCall
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .flatMap(entityDao::createEntity);
  }

  /**
   * Stores the entity retrieved from service whose primary key value equals {@code id} into the db.
   *
   * @param serviceCall the {@link Single} that makes the service call
   * @return a {@link Completable} that indicates whether or not the entity could be created
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable createEntity(@NonNull Single<E> serviceCall) {
    return createEntityAndGetValue(serviceCall)
        .toCompletable();
  }

  /**
   * Retrieves the entity from the service and updates it locally.
   *
   * @param entity the entity to update
   * @param serviceCall the function that makes the service call
   * @return the updated entity
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> updateEntityAndGetValue(@NonNull E entity, @NonNull BiFunction<Id, E, Completable> serviceCall) {
    return Optional.ofNullable(entity.getId())
        .map(id -> updateEntityAndGetValue(entity, serviceCall.apply(entity.getId(), entity)))
        .orElse(Single.error(new IllegalStateException("EntityId id cannot be null")));
  }

  /**
   * Updates the local entity with the values from {@code entity} if the {@code completable} completes successfully.
   *
   * @param entity the entity to update
   * @param completable the {@link Completable} that, if successful, will trigger the update
   * @return the updated entity
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> updateEntityAndGetValue(E entity, Completable completable) {
    return completable
        .compose(entityServiceProvider.applyCompletableServiceTransformation())
        .toSingleDefault(entity)
        .flatMap(entityDao::updateEntity);
  }

  /**
   * Updates the {@code entity} with the contents from the service call.
   *
   * @param entity the entity to update
   * @param serviceCall the function that makes the service call
   * @return a {@link Completable} that indicates whether or not the entity could be updated
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable updateEntity(E entity, @NonNull BiFunction<Id, E, Completable> serviceCall) {
    return updateEntityAndGetValue(entity, serviceCall)
        .toCompletable();
  }

  /**
   * Makes the service call to delete the entity and also deletes it locally.
   *
   * @param serviceCall the function that makes the service call
   * @param entityId the value of the primary key of the entity to be deleted
   * @return a {@link Completable} that indicates whether or not the entity could be deleted
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull Id entityId) {
    return serviceCall.apply(entityId)
        .compose(entityServiceProvider.applyCompletableServiceTransformation())
        .concatWith(entityDao.deleteEntityWithId(entityId));
  }

  /**
   * Makes the service call to delete the entity and also deletes it locally.
   *
   * @param serviceCall the function that makes the service call
   * @param entity the entity to be deleted
   * @return a {@link Completable} that indicates whether or not the entity could be deleted
   */
  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull E entity) {
    return Optional.of(entity.getId())
        .map(id -> serviceCall.apply(id)
            .compose(entityServiceProvider.applyCompletableServiceTransformation())
            .concatWith(entityDao.deleteEntity(entity)))
        .orElse(Completable.error(() -> new IllegalStateException("Entity id cannot be null")));
  }
}
