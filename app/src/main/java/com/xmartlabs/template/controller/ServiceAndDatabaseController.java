package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.xmartlabs.template.helper.function.BiFunction;
import com.xmartlabs.template.helper.function.Function;
import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ServiceAndDatabaseController<Id, D extends EntityWithId<Id>, C> extends Controller {
  private final DatabaseProvider<Id, D, C> databaseProvider;
  private final ServiceProvider<Id, D> serviceProvider;

  @CheckResult
  @NonNull
  @SuppressWarnings("unchecked")
  protected Flowable<List<D>> getEntities(@NonNull Single<List<D>> serviceCall, @NonNull C... conditions) {
    //noinspection unchecked
    return Flowable
        .concatArrayDelayError(
            databaseProvider.getEntities(conditions).toFlowable(),
            serviceProvider.getEntities(serviceCall).toFlowable()
        )
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .scan((databaseEntities, serviceEntities) ->
            databaseProvider.updateEntities(serviceEntities, conditions).blockingGet()
        )
        .observeOn(AndroidSchedulers.mainThread());
  }

  @CheckResult
  @NonNull
  protected Observable<D> getEntity(@NonNull Function<Id, Single<D>> serviceCall, Id id) {
    if (id == null) {
      return Observable.error(new IllegalStateException("Id cannot be null"));
    }
    return Observable.mergeDelayError(
        databaseProvider.getEntity(id).toObservable(),
        getServiceEntity(serviceCall, id).toObservable()
    );
  }

  @CheckResult
  @NonNull
  protected Single<D> getEntityFromDbIfPresentOrGetFromService(@NonNull Function<Id, Single<D>> serviceCall, Id id) {
    if (id == null) {
      return Single.error(new IllegalStateException("Id cannot be null"));
    }
    return databaseProvider.getEntity(id)
        .switchIfEmpty(getServiceEntity(serviceCall, id).toMaybe())
        .toSingle();

  }

  @CheckResult
  @NonNull
  protected Single<D> getServiceEntity(@NonNull Function<Id, Single<D>> serviceCall, Id id) {
    return makeIoCall(serviceCall.apply(id))
        .doOnSuccess(databaseProvider::updateEntity);
  }

  @CheckResult
  @NonNull
  protected Single<D> createEntityAndGetValue(@NonNull Single<D> serviceCall) {
    return makeIoCall(serviceCall)
        .flatMap(databaseProvider::createEntity);
  }

  @CheckResult
  @NonNull
  protected Completable createEntity(@NonNull Single<D> serviceCall) {
    return createEntityAndGetValue(serviceCall)
        .toCompletable();
  }

  @CheckResult
  @NonNull
  protected Single<D> updateEntityAndGetValue(D entity, @NonNull BiFunction<Id, D, Completable> serviceCall) {
    Id entityId = entity.getId();
    if (entityId == null) {
      return Single.error(new IllegalStateException("EntityId id cannot be null"));
    }

    return updateEntityAndGetValue(entity, serviceCall.apply(entity.getId(), entity));
  }

  @CheckResult
  @NonNull
  protected Single<D> updateEntityAndGetValue(D entity, Completable completable) {
    return makeIoCall(completable)
        .toSingleDefault(entity)
        .flatMap(databaseProvider::updateEntity);
  }

  @CheckResult
  @NonNull
  protected Completable updateEntity(D entity, @NonNull BiFunction<Id, D, Completable> serviceCall) {
    return updateEntityAndGetValue(entity, serviceCall)
        .toCompletable();
  }

  @CheckResult
  @NonNull
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull Id entityId) {
    return makeIoCall(serviceCall.apply(entityId))
        .concatWith(databaseProvider.deleteEntityFromId(entityId));
  }

  @CheckResult
  @NonNull
  protected Completable deleteEntity(@NonNull Function<Id, Completable> serviceCall, @NonNull D entity) {
    Id entityId = entity.getId();
    if (entityId == null) {
      return Completable.error(() -> new IllegalStateException("Entity id cannot be null"));
    }

    return makeIoCall(serviceCall.apply(entityId))
        .concatWith(databaseProvider.deleteEntityFromEntity(entity));
  }

  @NonNull
  protected abstract Class<D> getModelClass();

  @NonNull
  protected abstract Property<Id> getIdProperty();
}
