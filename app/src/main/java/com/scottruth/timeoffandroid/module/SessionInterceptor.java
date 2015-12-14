package com.scottruth.timeoffandroid.module;

import com.scottruth.timeoffandroid.TimeOffApplication;
import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.model.Session;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

import lombok.val;

/**
 * Created by santiago on 31/08/15.
 */
public class SessionInterceptor implements Interceptor {
    // TODO: used by HTTP headers
//    private static final String HEADER_USER_ID = "user-id";
//    private static final String HEADER_SESSION_TOKEN = "session-token";

    @Inject
    SessionController sessionController;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (sessionController == null) {
            TimeOffApplication.getContext().inject(this); // Can't do this in constructor because it's called in a module.
        }

        Session authResponse = sessionController.getSession();

        if (authResponse == null) {
            return chain.proceed(chain.request());
        } else {
            val newRequest = chain.request().newBuilder()
                    // TODO: Add auth token here if needed
//                    .addHeader(HEADER_SESSION_TOKEN, authResponse.getFacebook().getAccess_token())
//                    .addHeader(HEADER_USER_ID, authResponse.get_id())
                    .build();

            return chain.proceed(newRequest);
        }
    }
}
