package com.xmartlabs.bigbang.core;

import com.tspoon.traceur.Traceur;
import com.tspoon.traceur.TraceurConfig;
import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper;

import org.junit.Test;

import java.security.GeneralSecurityException;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeneralErrorHelperTest {
  private Integer calls;

  private String generateException() throws GeneralSecurityException {
    throw new GeneralSecurityException("Something went wrong");
  }

  private void initializeLogger() {
    calls = 0;
    GeneralErrorHelper generalErrorHelper = new GeneralErrorHelper();
    generalErrorHelper.setErrorHandlerForThrowable(GeneralSecurityException.class, throwable -> calls++);
    TraceurConfig config = new TraceurConfig(
        true,
        Traceur.AssemblyLogLevel.NONE,
        generalErrorHelper.getGeneralErrorAction()::accept);
    Traceur.enableLogging(config);
  }

  @Test
  public void multipleTimesSameException() {
    initializeLogger();
    Observable.fromCallable(this::generateException)
        .map(String::trim)
        .map(String::trim)
        .subscribe();
    assertThat(calls, equalTo(1));
  }

  @Test
  public void multipleTimesDifferentExceptions() {
    initializeLogger();
    Observable.fromCallable(this::generateException)
        .map(String::trim)
        .map(String::trim)
        .subscribe();
    Observable.fromCallable(this::generateException)
        .map(String::trim)
        .map(String::trim)
        .subscribe();
    assertThat(calls, equalTo(2));
  }
}
