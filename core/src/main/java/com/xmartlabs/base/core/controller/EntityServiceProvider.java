package com.xmartlabs.base.core.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.base.core.model.EntityWithId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

/**
 * Provider used to make the service calls related to the entity {@link E}.
 *
 * @param <Id> Type of the entity id
 * @param <E>  Entity of the Provider
 */
public interface EntityServiceProvider<Id, E extends EntityWithId<Id>> {
  /**
   * Provides the service {@link Single} transformation.
   * It could be used to sign out the user when getting a service error for example.
   *
   * @param <S> Type of the item emitted by the {@link Single}
   * @return The {@link Single} transformation
   */
  @CheckResult
  @NonNull
  <S> SingleTransformer<S, S> applySingleServiceTransformation();

  /**
   * Provides the service {@link Completable} transformation.
   * It could be used to sign out the user when getting a service error for example.
   *
   * @return The {@link Completable} transformation
   */
  @CheckResult
  @NonNull
  CompletableTransformer applyCompletableServiceTransformation();

  /**
   * Provides an entity {@link E} from a service which gives a list of {@link E}.
   * It could be used when there is not a get entity service endpoint for example.
   *
   * @param serviceCall The {@link Single} which has the service call
   * @param id          The id of the entity
   * @return The {@link Single} transformation
   */
  @CheckResult
  @NonNull
  Maybe<E> getEntityFromList(@NonNull Single<List<E>> serviceCall, @NonNull Id id);
}
