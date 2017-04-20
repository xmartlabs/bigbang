package com.xmartlabs.template;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.xmartlabs.base.core.Injector;
import com.xmartlabs.base.core.helper.GeneralErrorHelper;
import com.xmartlabs.base.core.log.LoggerTree;
import com.xmartlabs.base.core.model.BuildInfo;
import com.xmartlabs.base.core.rx.error.CompletableObserverWithErrorHandling;
import com.xmartlabs.base.core.rx.error.FlowableObserverWithErrorHandling;
import com.xmartlabs.base.core.rx.error.MaybeObserverWithErrorHandling;
import com.xmartlabs.base.core.rx.error.ObserverWithErrorHandling;
import com.xmartlabs.base.core.rx.error.SingleObserverWithErrorHandling;
import com.xmartlabs.base.log.crashlytics.CrashlyticsLogger;
import com.xmartlabs.base.retrofit.helper.ServiceErrorHandler;
import com.xmartlabs.template.module.AndroidModule;

import javax.inject.Inject;

import bullet.ObjectGraph;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

public class BaseProjectApplication extends Application {
  private static BaseProjectApplication instance;

  private ObjectGraph bullet;

  @Inject
  BuildInfo buildInfo;
  @Inject
  GeneralErrorHelper generalErrorHelper;
  @Inject
  LoggerTree loggerTree;
  @Inject
  ServiceErrorHandler serviceErrorHandler;

  public BaseProjectApplication() {
    instance = this;
  }

  public static BaseProjectApplication getContext() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initializeThreeTenABP();
    initializeInjections();
    initializeDataBase();
    initializeRxErrorHandler();
    initializeLogging(); // Crashlytics initialization should go at the end.
  }

  private void initializeInjections() {
    ApplicationComponent component = createComponent();
    bullet = createBullet(component);
    Injector.getInstance().setObjectGraph(bullet);
    Injector.inject(this);
  }

  protected ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .coreAndroidModule(new AndroidModule(this))
        .build();
  }

  protected ObjectGraph createBullet(ApplicationComponent component) {
    return new BulletApplicationComponent(component);
  }

  private void initializeDataBase() {
    FlowManager.init(new FlowConfig.Builder(this).build());
  }

  private void initializeThreeTenABP() {
    AndroidThreeTen.init(this);
  }

  private void initializeLogging() {
    loggerTree.addLogger(new CrashlyticsLogger().initialize(buildInfo, this));
    Timber.plant(loggerTree);
  }

  public <T> T inject(final T t) {
    return bullet.inject(t);
  }

  @SuppressWarnings("unchecked")
  private void initializeRxErrorHandler() {
    serviceErrorHandler.handleServiceErrors();

    RxJavaPlugins.setOnSingleSubscribe((single, singleObserver) ->
        new SingleObserverWithErrorHandling<>(singleObserver, generalErrorHelper.getGeneralErrorAction()));
    RxJavaPlugins.setOnObservableSubscribe((observable, observer) ->
        new ObserverWithErrorHandling<>(observer, generalErrorHelper.getGeneralErrorAction()));
    RxJavaPlugins.setOnMaybeSubscribe((maybe, maybeObserver) ->
        new MaybeObserverWithErrorHandling<>(maybeObserver, generalErrorHelper.getGeneralErrorAction()));
    RxJavaPlugins.setOnFlowableSubscribe((flowable, subscriber) ->
        new FlowableObserverWithErrorHandling<>(subscriber, generalErrorHelper.getGeneralErrorAction()));
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, generalErrorHelper.getGeneralErrorAction()));
  }
}
