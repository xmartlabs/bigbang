package com.xmartlabs.base.retrofit.helper;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.base.core.helper.GeneralErrorHelper;
import com.xmartlabs.base.core.helper.ServiceHelper;
import com.xmartlabs.base.core.helper.function.Consumer;
import com.xmartlabs.base.core.log.LoggerTree;
import com.xmartlabs.base.retrofit.exception.ServiceExceptionWithMessage;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import retrofit2.HttpException;

/**
 * Handles {@link HttpException} and {@link ServiceExceptionWithMessage} exceptions emitted by the
 * {@link GeneralErrorHelper}.
 */
public class ServiceErrorHandler {
  private static final String LOGGER_KEY_RESPONSE_BODY = "response_body";
  private static final String LOGGER_KEY_RESPONSE_HEADERS = "response_headers";
  private static final String LOGGER_KEY_STATUS_CODE = "status_code";
  private static final String LOGGER_KEY_URL = "url";

  private final GeneralErrorHelper generalErrorHelper;
  private LoggerTree loggerTree;

  @Getter(AccessLevel.PRIVATE)
  private final Consumer<Throwable> serviceErrorHandler = t -> {
    ServiceExceptionWithMessage serviceExceptionWithMessage = (ServiceExceptionWithMessage) t;

    Map<String, String> information = new HashMap<>();
    information.put(LOGGER_KEY_URL, ServiceHelper.getUrl(serviceExceptionWithMessage.getResponse().raw()));
    information.put(LOGGER_KEY_STATUS_CODE, String.valueOf(serviceExceptionWithMessage.getCode()));
    information.put(LOGGER_KEY_RESPONSE_HEADERS, serviceExceptionWithMessage.getResponse().headers().toString());
    information.put(LOGGER_KEY_RESPONSE_BODY, serviceExceptionWithMessage.getErrorBody());

    loggerTree.log(information, serviceExceptionWithMessage);
  };

  @Inject
  public ServiceErrorHandler(@NonNull GeneralErrorHelper generalErrorHelper, @NonNull LoggerTree loggerTree) {
    this.generalErrorHelper = generalErrorHelper;
    this.loggerTree = loggerTree;
  }

  /**
   * Handles the {@link HttpException} and {@link ServiceExceptionWithMessage} exceptions, logging them if any error
   * is present.
   */
  public void handleServiceErrors() {
    generalErrorHelper.setErrorHandlerForThrowable(ServiceExceptionWithMessage.class, serviceErrorHandler);
    generalErrorHelper.setErrorHandlerForThrowable(HttpException.class, throwable ->
        Optional.ofNullable((HttpException) throwable)
          .ifPresent(t -> serviceErrorHandler.accept(new ServiceExceptionWithMessage(t)))
    );
  }
}
