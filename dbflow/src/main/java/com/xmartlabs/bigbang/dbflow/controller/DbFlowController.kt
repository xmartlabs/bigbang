package com.xmartlabs.bigbang.dbflow.controller

import android.support.annotation.CheckResult
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.SQLOperator
import com.raizlabs.android.dbflow.sql.language.property.Property
import com.raizlabs.android.dbflow.structure.BaseModel
import com.xmartlabs.bigbang.core.controller.Controller
import com.xmartlabs.bigbang.core.controller.EntityDao
import com.xmartlabs.bigbang.core.extensions.observeOnIo
import com.xmartlabs.bigbang.core.model.EntityWithId
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.reflect.KClass

/** [EntityDao] implementation using DbFlow ORM */
open class DbFlowController<Id, D>(val modelClass: KClass<D>, val propertyId: Property<Id>)
    : Controller(), EntityDao<Id, D, SQLOperator> where D : BaseModel, D: EntityWithId<Id> {

  @CheckResult
  override fun getEntities(vararg conditions: SQLOperator) = select.from(modelClass.java).where(*conditions).rx().list.observeOnIo()

  @CheckResult
  override fun deleteAndInsertEntities(newEntities: List<D>, vararg conditions: SQLOperator) =
      delete(modelClass).where(*conditions).rx().execute()
          .observeOnIo()
          .flatMap { newEntities.queryAction(BaseModel::save) }

  @CheckResult
  override fun getEntity(id: Id): Maybe<D> =
      Maybe.fromCallable { select.from(modelClass.java).where(propertyId.eq(id)).querySingle() }

  @CheckResult
  override fun getEntity(vararg conditions: SQLOperator): Maybe<D> =
      Maybe.fromCallable { select.from(modelClass.java).where(*conditions).querySingle() }

  @CheckResult
  override fun createEntity(entity: D) = entity.queryAction(BaseModel::save).observeOnIo()

  @CheckResult
  override fun updateEntity(entity: D) = entity.queryAction(BaseModel::update).observeOnIo()

  @CheckResult
  override fun deleteEntityWithId(entityId: Id) =
      delete(modelClass).where(propertyId.eq(entityId)).rx().execute().toCompletable().observeOnIo()

  @CheckResult
  override fun deleteEntity(entity: D) = entity.queryAction(BaseModel::delete).toCompletable().observeOnIo()
}

fun <T : BaseModel> T.queryAction(action: (T) -> Any) = Single.fromCallable { action(this); this }

fun <D : BaseModel, T : Collection<D>> T.queryAction(action: (D) -> Any) =
    Observable.fromIterable(this).flatMap { it.queryAction(action).toObservable() }.toList()
