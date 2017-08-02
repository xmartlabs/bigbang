package com.xmartlabs.bigbang.retrofit.controller

import android.support.annotation.CheckResult
import com.xmartlabs.bigbang.core.controller.Controller
import com.xmartlabs.bigbang.core.controller.EntityServiceProvider
import com.xmartlabs.bigbang.core.model.EntityWithId
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Controller used to make the service calls related to the entity [E].
 * @param <Id> Type of the entity id
 * *
 * @param <E> Entity of the controller
 */
class ServiceController<Id, E : EntityWithId<Id>> : Controller(), EntityServiceProvider<Id, E> {
  @CheckResult
  override fun getEntityFromList(serviceCall: Single<List<E>>, id: Id) = serviceCall
      .applyIoSchedulers()
      .flatMapObservable { it -> Observable.fromIterable(it) }
      .filter { it == id }
      .firstElement()
}
