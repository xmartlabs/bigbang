package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.xmartlabs.base.core.exception.ServiceExceptionWithMessage;
import com.xmartlabs.template.model.EntityWithId;

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

  @CheckResult
  @NonNull
  @Override
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
