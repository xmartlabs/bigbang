package com.scottruth.timeoffandroid.service;

import com.scottruth.timeoffandroid.model.AuthResponse;
import com.scottruth.timeoffandroid.model.LoginRequest;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by santiago on 31/08/15.
 */
public interface AuthService {
    int HTTP_UNAUTHORIZED = 401;

    @POST("login")
    Observable<AuthResponse> login(@Body LoginRequest loginRequest);
}
