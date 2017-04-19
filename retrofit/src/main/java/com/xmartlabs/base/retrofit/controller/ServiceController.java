package com.xmartlabs.base.retrofit.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.xmartlabs.base.core.controller.Controller;
import com.xmartlabs.base.core.controller.EntityServiceProvider;
import com.xmartlabs.base.core.model.EntityWithId;
import com.xmartlabs.base.retrofit.exception.ServiceExceptionWithMessage;

import java.util.List;

import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

/**
 * Controller used to make the service calls related to the entity {@link E}.
 *
 * @param <Id> Type of the entity id
 * @param <E> Entity of the controller
 */
public class ServiceController<Id, E extends EntityWithId<Id>> extends Controller
    implements EntityServiceProvider<Id, E> {
  @CheckResult
  @NonNull
  @Override
  public <S> SingleTransformer<S, S> applySingleServiceTransformation() {
    return applySingleIoSchedulers();
  }

  @CheckResult
  @NonNull
  @Override
  public CompletableTransformer applyCompletableServiceTransformation() {
    return applyCompletableIoSchedulers();
  }

  /**
   * Provides the service {@link Single} transformation and gets the {@link S} value from the response.
   * It could be used to sign out the user when getting a service error for example.
   *
   * @param <S> Type of the item emitted by the {@link Single}
   * @return The {@link Single} transformation
   */
  @CheckResult
  @NonNull
  public <S> SingleTransformer<Response<S>, S> applySingleServiceTransformationAndGetResponse() {
    return upstream -> upstream
        .compose(applySingleServiceTransformation())
        .map(response -> {
          if (response.isSuccessful()) {
            return response.body();
          } else {
            throw new ServiceExceptionWithMessage(response);
          }
        });
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
