package com.xmartlabs.template;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tspoon.traceur.Traceur;
import com.tspoon.traceur.TraceurConfig;
import com.xmartlabs.bigbang.core.Injector;
import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper;
import com.xmartlabs.bigbang.core.log.LoggerTree;
import com.xmartlabs.bigbang.core.model.BuildInfo;
import com.xmartlabs.bigbang.log.crashlytics.CrashlyticsLogger;
import com.xmartlabs.bigbang.retrofit.helper.ServiceErrorHandler;
import com.xmartlabs.template.module.AndroidModule;

import javax.inject.Inject;

import bullet.ObjectGraph;
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

    TraceurConfig config = new TraceurConfig(
        true,
        BuildConfig.DEBUG ? Traceur.AssemblyLogLevel.SHOW_ALL : Traceur.AssemblyLogLevel.NONE,
        generalErrorHelper.getGeneralErrorAction()::accept);
    Traceur.enableLogging(config);
  }
}
