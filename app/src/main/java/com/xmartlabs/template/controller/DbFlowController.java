package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xmartlabs.template.database.AppDataBase;
import com.xmartlabs.template.model.DatabaseModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DbFlowController<Id, D extends DatabaseModel<Id>> extends Controller
    implements DatabaseProvider<Id, D, SQLCondition> {
  @Getter(AccessLevel.PRIVATE)
  final Class<D> modelClass;
  @Getter(AccessLevel.PRIVATE)
  final Property<Id> idProperty;

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
  public Single<List<D>> updateEntities(List<D> newEntities, @NonNull SQLCondition... conditions) {
    return Single
        .fromCallable(() -> {
          FlowManager.getDatabase(AppDataBase.class)
              .executeTransaction(databaseWrapper -> {
                SQLite.delete(getModelClass())
                    .where(conditions)
                    .execute();
                Stream.of(newEntities)
                    .forEach(BaseModel::save);
              });
          return newEntities;
        })
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Maybe<D> getEntity(Id id) {
    return Maybe
        .fromCallable(() -> new Select()
            .from(getModelClass())
            .where(getIdProperty().eq(id))
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
  public Completable deleteEntityFromId(Id entityId) {
    return Completable
        .fromAction(() ->
        new Delete()
            .from(getModelClass())
            .where(getIdProperty().eq(entityId))
            .execute())
        .subscribeOn(Schedulers.io());
  }

  @CheckResult
  @NonNull
  @Override
  public Completable deleteEntityFromEntity(D entity) {
    return Completable.fromAction(entity::delete)
        .subscribeOn(Schedulers.io());
  }
}
