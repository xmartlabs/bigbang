package com.xmartlabs.bigbang.retrofit.exception;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.helper.ServiceHelper;

import javax.inject.Inject;

import lombok.Getter;
import retrofit2.HttpException;
import retrofit2.Response;

@Getter
public final class ServiceExceptionWithMessage extends RuntimeException {
  private final int code;
  private final String message;
  @Nullable
  private final String errorBody;
  private final transient Response<?> response;

  @Inject
  Gson gson;

  private ServiceExceptionWithMessage(Response<?> response) {
    super("HTTP " + response.code() + " " + response.message());
    //noinspection ThrowableResultOfMethodCallIgnored
    Injector.inject(this);

    code = response.code();
    message = response.message();
    errorBody = ServiceHelper.cloneResponseAndGetAsString(response.errorBody().source());
    this.response = response;
  }

  public ServiceExceptionWithMessage(HttpException exception) {
    this(exception.response());
    initCause(exception);
  }
}
