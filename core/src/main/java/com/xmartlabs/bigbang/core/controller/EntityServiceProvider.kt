package com.xmartlabs.bigbang.core.controller

import androidx.annotation.CheckResult
import com.xmartlabs.bigbang.core.model.EntityWithId
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Provider used to make the service calls related to the entity [E].
 * @param <Id> Type of the entity id
 * *
 * @param <E>  Entity of the Provider
 */
interface EntityServiceProvider<Id, E : EntityWithId<Id>> {
  /**
   * Provides an entity [E] from a service which gives a list of [E].
   * It could be used when there is not a get entity service endpoint for example.
   * @param serviceCall The [Single] which has the service call
   * *
   * @param id The id of the entity
   * *
   * @return The [Single] transformation
   */
  @CheckResult
  fun getEntityFromList(serviceCall: Single<List<E>>, id: Id): Maybe<E>
}
