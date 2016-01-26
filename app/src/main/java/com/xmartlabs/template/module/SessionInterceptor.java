package com.xmartlabs.template.module;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.model.Session;

import java.io.IOException;

import javax.inject.Inject;

import lombok.val;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by santiago on 31/08/15.
 */
public class SessionInterceptor implements Interceptor {
  @Inject
  SessionController sessionController;

  @Override
  public Response intercept(Chain chain) throws IOException {
    if (sessionController == null) {
      BaseProjectApplication.getContext().inject(this); // Can't do this in constructor because it's called in a module.
    }

    Session authResponse = sessionController.getSession();

    if (authResponse == null) {
      return chain.proceed(chain.request());
    } else {
      val newRequest = chain.request().newBuilder()
          //.addHeader("session", sessionInfo) // TODO: Add auth token here if needed
          .build();

      return chain.proceed(newRequest);
    }
  }
}
