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
public abstract class EntityController<Id, D extends EntityWithId<Id>, C> extends Controller {
  private final EntityDao<Id, D, C> entityDao;
  private final EntityServiceProvider<Id, D> entityServiceProvider;

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
  protected Flowable<List<D>> getEntities(@NonNull Single<List<D>> serviceCall, @NonNull C... conditions) {
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
  protected Observable<D> getEntity(@NonNull Function<Id, Single<D>> serviceCall, @NonNull Id id) {
    return Observable.mergeDelayError(
        entityDao.getEntity(id).toObservable(),
        getServiceEntity(serviceCall, id).toObservable()
    );
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<D> getEntityFromDbIfPresentOrGetFromService(@NonNull Function<Id, Single<D>> serviceCall,
                                                               @NonNull Id id) {
    return entityDao.getEntity(id)
        .switchIfEmpty(getServiceEntity(serviceCall, id).toMaybe())
        .toSingle();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<D> getServiceEntity(@NonNull Function<Id, Single<D>> serviceCall, Id id) {
    return serviceCall.apply(id)
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .doOnSuccess(entityDao::updateEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<D> createEntityAndGetValue(@NonNull Single<D> serviceCall) {
    return serviceCall
        .compose(entityServiceProvider.applySingleServiceTransformation())
        .flatMap(entityDao::createEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable createEntity(@NonNull Single<D> serviceCall) {
    return createEntityAndGetValue(serviceCall)
        .toCompletable();
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<D> updateEntityAndGetValue(@NonNull D entity, @NonNull BiFunction<Id, D, Completable> serviceCall) {
    return Optional.ofNullable(entity.getId())
        .map(id -> updateEntityAndGetValue(entity, serviceCall.apply(entity.getId(), entity)))
        .orElse(Single.error(new IllegalStateException("EntityId id cannot be null")));
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Single<D> updateEntityAndGetValue(D entity, Completable completable) {
    return completable
        .compose(entityServiceProvider.applyCompletableServiceTransformation())
        .toSingleDefault(entity)
        .flatMap(entityDao::updateEntity);
  }

  @CheckResult
  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected Completable updateEntity(D entity, @NonNull BiFunction<Id, D, Completable> serviceCall) {
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
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull D entity) {
    return Optional.of(entity.getId())
        .map(id -> serviceCall.apply(id)
            .compose(entityServiceProvider.applyCompletableServiceTransformation())
            .concatWith(entityDao.deleteEntity(entity)))
        .orElse(Completable.error(() -> new IllegalStateException("Entity id cannot be null")));
  }

  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected abstract Class<D> getModelClass();

  @NonNull
  @SuppressWarnings({"unused", "WeakerAccess"})
  protected abstract Property<Id> getIdProperty();
}
