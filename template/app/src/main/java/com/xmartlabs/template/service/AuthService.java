package com.xmartlabs.template.service;

import com.xmartlabs.template.model.AuthResponse;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface AuthService {
  //TODO: replace with url path to get access token
  String URL_ACCESS_TOKEN = "";

  //TODO: change signature to the required one to fetch the access token and check AuthResponse to match response
  @POST(URL_ACCESS_TOKEN)
  Single<AuthResponse> getAccessToken();
}
