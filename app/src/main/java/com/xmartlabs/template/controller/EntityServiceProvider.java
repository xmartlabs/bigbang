package com.xmartlabs.template.controller;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.xmartlabs.template.model.EntityWithId;

import java.util.List;

import io.reactivex.CompletableTransformer;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

public interface EntityServiceProvider<T, D extends EntityWithId<T>> {
  @CheckResult
  @NonNull
  <S> SingleTransformer<S, S> applySingleServiceTransformation();

  @CheckResult
  @NonNull
  CompletableTransformer applyCompletableServiceTransformation();

  @CheckResult
  @NonNull
  <S> SingleTransformer<Response<S>, S> applySingleServiceTransformationAndGetResponse();

  @CheckResult
  @NonNull
  Maybe<D> getEntityFromList(@NonNull Single<List<D>> serviceCall, T id);
}
