package com.xmartlabs.template.service.demo;

import com.xmartlabs.template.model.demo.DemoRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Single;

/**
 * Created by remer on 10/12/2015.
 * TODO: Just for example purpose. Delete this class and all its references in the real project
 */
public interface DemoService {
  @GET("repositories")
  @Headers({"Accept: application/vnd.github.v3+json"})
  Single<List<DemoRepo>> repositories();
}
