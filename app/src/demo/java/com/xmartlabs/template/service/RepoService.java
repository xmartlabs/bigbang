package com.xmartlabs.template.service;

import com.xmartlabs.template.model.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Single;

/**
 * Created by remer on 10/12/2015.
 */
public interface RepoService {
  @GET("repositories")
  @Headers({"Accept: application/vnd.github.v3+json"})
  Single<List<Repo>> repositories();
}
