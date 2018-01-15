package com.xmartlabs.bigbang.retrofit.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.xmartlabs.bigbang.core.controller.Controller;
import com.xmartlabs.bigbang.core.controller.EntityServiceProvider;
import com.xmartlabs.bigbang.core.model.EntityWithId;

import java.util.List;

import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

/**
 * Controller used to make the service calls related to the entity {@link E}.
 *
 * @param <Id> Type of the entity id
 * @param <E> Entity of the controller
 */
public abstract class ServiceController<Id, E extends EntityWithId<Id>> extends Controller
    implements EntityServiceProvider<Id, E> {
  @CheckResult
  @NonNull
  @Override
  public CompletableTransformer applyCompletableServiceTransformation() {
    return applyCompletableIoSchedulersTransformation();
  }

  @CheckResult
  @NonNull
  @Override
  public <S> MaybeTransformer<S, S> applyMaybeServiceTransformation() {
    return applyMaybeIoSchedulersTransformation();
  }

  @CheckResult
  @NonNull
  @Override
  public <S> SingleTransformer<S, S> applySingleServiceTransformation() {
    return applySingleIoSchedulersTransformation();
  }

  @CheckResult
  @NonNull
  @Override
  public Maybe<E> getEntityFromList(@NonNull Single<List<E>> serviceCall, @NonNull Id id) {
    return serviceCall
        .compose(applySingleServiceTransformation())
        .toObservable()
        .flatMap(Observable::fromIterable)
        .filter(entity -> Objects.equals(entity, id))
        .firstElement();
  }
}
