package com.xmartlabs.template;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

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
import com.xmartlabs.template.module.OkHttpModule;
import com.xmartlabs.template.module.RestServiceModule;
import com.xmartlabs.template.module.ServiceGsonModule;

import javax.inject.Inject;

import bullet.ObjectGraph;
import timber.log.Timber;

public class TemplateApplication extends Application {
  private static TemplateApplication instance;

  @Inject
  BuildInfo buildInfo;
  @Inject
  GeneralErrorHelper generalErrorHelper;
  @Inject
  LoggerTree loggerTree;
  @Inject
  ServiceErrorHandler serviceErrorHandler;

  public TemplateApplication() {
    instance = this;
  }

  public static TemplateApplication getContext() {
    return instance;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || !BuildConfig.DEBUG) {
      MultiDex.install(this);
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    initializeThreeTenABP();
    initializeInjections();
    initializeDataBase();
    initializeRxErrorHandler();
    initializeLogging();
  }

  private void initializeInjections() {
    ApplicationComponent component = createComponent();
    ObjectGraph bullet = createBullet(component);
    Injector.getInstance().setObjectGraph(bullet);
    Injector.inject(this);
  }

  protected ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .coreAndroidModule(new AndroidModule(this))
        .restServiceModule(new RestServiceModule())
        .okHttpModule(new OkHttpModule())
        .serviceGsonModule(new ServiceGsonModule())
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
    //TODO: Configure Fabric and add Fabric apiSecret and apiKey properties file in the root folder
    loggerTree.addLogger(new CrashlyticsLogger().initialize(buildInfo, this));
    Timber.plant(loggerTree);
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
