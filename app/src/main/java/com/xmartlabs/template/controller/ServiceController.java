package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Objects;
import com.xmartlabs.template.common.exeption.ServiceExceptionWithMessage;
import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

public class ServiceController<T, D extends EntityWithId<T>> extends Controller
    implements EntityServiceProvider<T, D> {
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
  public Maybe<D> getEntityFromList(@NonNull Single<List<D>> serviceCall, @NonNull T id) {
    return serviceCall
        .compose(applySingleServiceTransformation())
        .toObservable()
        .flatMap(Observable::fromIterable)
        .filter(entity -> Objects.equals(entity, id))
        .firstElement();
  }
}
