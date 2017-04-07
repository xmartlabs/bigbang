package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.xmartlabs.template.helper.function.BiFunction;
import com.xmartlabs.template.helper.function.Function;
import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EntityController<Id, E extends EntityWithId<Id>, Condition> extends Controller {
  private final EntityDao<Id, E, Condition> entityDao;
  private final EntityServiceProvider<Id, E> entityServiceProvider;

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

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Observable<E> getEntity(@NonNull Function<Id, Single<E>> serviceCall, @NonNull Id id) {
    return Observable.mergeDelayError(
        entityDao.getEntity(id).toObservable(),
        getServiceEntity(serviceCall, id).toObservable()
    );
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> getEntityFromDbIfPresentOrGetFromService(@NonNull Function<Id, Single<E>> serviceCall,
                                                               @NonNull Id id) {
    return entityDao.getEntity(id)
        .switchIfEmpty(getServiceEntity(serviceCall, id).toMaybe())
        .toSingle();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> getServiceEntity(@NonNull Function<Id, Single<E>> serviceCall, Id id) {
    return serviceCall.apply(id)
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .doOnSuccess(entityDao::updateEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> createEntityAndGetValue(@NonNull Single<E> serviceCall) {
    return serviceCall
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .flatMap(entityDao::createEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable createEntity(@NonNull Single<E> serviceCall) {
    return createEntityAndGetValue(serviceCall)
        .toCompletable();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> updateEntityAndGetValue(@NonNull E entity, @NonNull BiFunction<Id, E, Completable> serviceCall) {
    return Optional.ofNullable(entity.getId())
        .map(id -> updateEntityAndGetValue(entity, serviceCall.apply(entity.getId(), entity)))
        .orElse(Single.error(new IllegalStateException("EntityId id cannot be null")));
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<E> updateEntityAndGetValue(E entity, Completable completable) {
    return completable
        .compose(entityServiceProvider.applyCompletableServiceTransformation())
        .toSingleDefault(entity)
        .flatMap(entityDao::updateEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable updateEntity(E entity, @NonNull BiFunction<Id, E, Completable> serviceCall) {
    return updateEntityAndGetValue(entity, serviceCall)
        .toCompletable();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull Id entityId) {
    return serviceCall.apply(entityId)
        .compose(entityServiceProvider.applyCompletableServiceTransformation())
        .concatWith(entityDao.deleteEntityWithId(entityId));
  }

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

  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected abstract Class<E> getModelClass();

  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected abstract Property<Id> getIdProperty();
}
