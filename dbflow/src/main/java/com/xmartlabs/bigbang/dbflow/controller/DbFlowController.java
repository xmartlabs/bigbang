package com.xmartlabs.bigbang.dbflow.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.bigbang.core.controller.Controller;
import com.xmartlabs.bigbang.core.controller.EntityDao;
import com.xmartlabs.bigbang.core.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** {@link EntityDao} implementation using DbFlow ORM */
@RequiredArgsConstructor
public abstract class DbFlowController<Id, D extends BaseModel & EntityWithId<Id>> extends Controller
    implements EntityDao<Id, D, SQLOperator> {
  @Getter(AccessLevel.PRIVATE)
  private final Class<D> modelClass;
  @Getter(AccessLevel.PRIVATE)
  private final Property<Id> propertyId;

  @CheckResult
  @NonNull
  @Override
  public Single<List<D>> getEntities(@NonNull SQLOperator... conditions) {
    return Single
        .fromCallable(() ->
            SQLite.select()
                .from(getModelClass())
                .where(conditions)
                .queryList()
        )
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Single<List<D>> deleteAndInsertEntities(@Nullable List<D> entitiesToInsert,
                                                 @NonNull SQLOperator... conditionsToDelete) {
    return Single
        .fromCallable(() -> {
          FlowManager.getDatabase(modelClass)
              .executeTransaction(databaseWrapper -> {
                SQLite.delete(getModelClass())
                    .where(conditionsToDelete)
                    .execute();
                Stream.ofNullable(entitiesToInsert)
                    .forEach(BaseModel::save);
              });
          return entitiesToInsert;
        })
        .compose(applySingleIoSchedulers());
  }

  @CheckResult
  @NonNull
  @Override
  public Maybe<D> getEntity(@NonNull Id id) {
    return Maybe
        .fromCallable(() -> new Select()
            .from(getModelClass())
            .where(getPropertyId().eq(id))
            .querySingle())
        .compose(applyMaybeIoSchedulers());
  }

  @CheckResult
  @NonNull
  @Override
  public Single<D> createEntity(@NonNull D entity) {
    return Completable.fromAction(entity::insert)
        .toSingleDefault(entity)
        .compose(applySingleIoSchedulers());
  }

  @CheckResult
  @NonNull
  @Override
  public Single<D> updateEntity(@NonNull D entity) {
    return Completable.fromAction(entity::update)
        .toSingleDefault(entity)
        .compose(applySingleIoSchedulers());
  }

  @CheckResult
  @NonNull
  @Override
  public Completable deleteEntityWithId(@NonNull Id entityId) {
    return Completable
        .fromAction(() ->
            new Delete()
                .from(getModelClass())
                .where(getPropertyId().eq(entityId))
                .execute())
        .compose(applyCompletableIoSchedulers());
  }

  @CheckResult
  @NonNull
  @Override
  public Completable deleteEntity(@NonNull D entity) {
    return Completable.fromAction(entity::delete)
        .compose(applyCompletableIoSchedulers());
  }
}
