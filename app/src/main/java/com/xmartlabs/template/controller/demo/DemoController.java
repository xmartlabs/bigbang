package com.xmartlabs.template.controller.demo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.controller.ServiceController;
import com.xmartlabs.template.model.demo.DemoRepo;
import com.xmartlabs.template.service.demo.DemoService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

// TODO: Just for demo purposes, delete this class in a real project

/**
 * Created by remer on 10/12/2015.
 */
public class DemoController extends ServiceController {

    @Inject
    DemoService demoService;

    public DemoController() {
        super();
        BaseProjectApplication.getContext().inject(this);
    }

    @NonNull
    public Observable<List<DemoRepo>> getPublicRepositoriesFilteredBy(@Nullable String filter) {
        return demoService.repositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(getGeneralErrorHelper().getGeneralErrorAction())
                .flatMap(Observable::from)
                .filter(repo -> repo.match(filter))
                .toList();
    }
}
