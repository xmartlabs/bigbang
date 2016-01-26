package com.xmartlabs.template.service;

import com.xmartlabs.template.model.AuthResponse;
import com.xmartlabs.template.model.LoginRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Single;

/**
 * Created by santiago on 31/08/15.
 */
public interface AuthService {
  @POST("login")
  Single<AuthResponse> login(@Body LoginRequest loginRequest);
}
