package com.scottruth.timeoffandroid.service.demo;

import com.scottruth.timeoffandroid.model.demo.DemoRepo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

// TODO: Just for demo purposes, delete this class in a real project

/**
 * Created by remer on 10/12/2015.
 */
public interface DemoService {
    // TODO: Just for example purpose. Delete this class and all its references in the real project

    @GET("repositories")
    @Headers({
        "Accept: application/vnd.github.v3+json"
    })
    Observable<List<DemoRepo>> repositories();
}
