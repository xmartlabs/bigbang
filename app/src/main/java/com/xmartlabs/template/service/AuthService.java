package com.xmartlabs.template.service;

import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.LoginRequest;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by santiago on 31/08/15.
 */
public interface AuthService {
  @POST("login")
  Observable<AuthResponse> login(@Body LoginRequest loginRequest);
}
