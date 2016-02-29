package com.xmartlabs.template.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xmartlabs.template.BaseProjectApplication;
import com.xmartlabs.template.model.Repo;
import com.xmartlabs.template.service.RepoService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by remer on 10/12/2015.
 */
public class RepoController extends ServiceController {
  @Inject
  RepoService repoService;

  public RepoController() {
    BaseProjectApplication.getContext().inject(this);
  }

  @NonNull
  public Observable<List<Repo>> getPublicRepositoriesFilteredBy(@Nullable String filter) {
    return repoService.repositories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(getGeneralErrorHelper().getGeneralErrorAction())
        .toObservable()
        .flatMap(Observable::from)
        .filter(repo -> repo.matches(filter))
        .toList();
  }
}
