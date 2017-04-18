package com.xmartlabs.base.dbflow.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.base.core.controller.Controller;
import com.xmartlabs.base.core.controller.EntityDao;
import com.xmartlabs.base.dbflow.model.DatabaseModel;

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
public abstract class DbFlowController<Id, D extends DatabaseModel<Id>> extends Controller
    implements EntityDao<Id, D, SQLCondition> {
  @Getter(AccessLevel.PRIVATE)
  private final Class<D> modelClass;
  @Getter(AccessLevel.PRIVATE)
  private final Property<Id> propertyId;

  @CheckResult
  @NonNull
  @Override
  public Single<List<D>> getEntities(@NonNull SQLCondition... conditions) {
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
                                                 @NonNull SQLCondition... conditionsToDelete) {
    return Single
        .fromCallable(() -> {
          FlowManager.getDatabase(getAppDataBaseClass())
              .executeTransaction(databaseWrapper -> {
                SQLite.delete(getModelClass())
                    .where(conditionsToDelete)
                    .execute();
                Stream.ofNullable(entitiesToInsert)
                    .forEach(BaseModel::save);
              });
          return entitiesToInsert;
        })
        .subscribeOn(Schedulers.io());
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
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Single<D> createEntity(@NonNull D entity) {
    return Completable.fromAction(entity::insert)
        .toSingleDefault(entity)
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Single<D> updateEntity(@NonNull D entity) {
    return Completable.fromAction(entity::update)
        .toSingleDefault(entity)
        .subscribeOn(Schedulers.io());
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
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Completable deleteEntity(@NonNull D entity) {
    return Completable.fromAction(entity::delete)
        .subscribeOn(Schedulers.io());
  }

  /**
   * Retrieves the type of the AppDataBase class
   * @param <T> the type of the AppDataBase class
   * @return the type of the AppDataBase class
   */
  protected abstract <T> Class<T> getAppDataBaseClass();
}
