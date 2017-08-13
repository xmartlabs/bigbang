package com.xmartlabs.template.service

import com.xmartlabs.template.model.AuthResponse

import io.reactivex.Single
import retrofit2.http.POST

interface AuthService {
  companion object {
    //TODO: replace with url path to get access token
    const val URL_ACCESS_TOKEN = ""
  }

  //TODO: change signature to the required one to fetch the access token and check AuthResponse to match response
  @get:POST(URL_ACCESS_TOKEN)
  val accessToken: Single<AuthResponse>
}
