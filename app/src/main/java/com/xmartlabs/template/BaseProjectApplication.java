package com.xmartlabs.template;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.xmartlabs.template.helper.GeneralErrorHelper;
import com.xmartlabs.template.module.AndroidModule;

import javax.inject.Inject;

import bullet.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import rx.plugins.RxJavaHooks;
import timber.log.Timber;

/**
 * Created by remer on 08/12/15.
 */
public class BaseProjectApplication extends Application {
  private static BaseProjectApplication instance;

  private ObjectGraph bullet;

  @Inject
  GeneralErrorHelper generalErrorHelper;

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
    inject(this);
  }

  protected ApplicationComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .androidModule(new AndroidModule(this))
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
    CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
        .disabled(BuildConfig.DEBUG)
        .build();
    Crashlytics crashlytics = new Crashlytics.Builder().core(crashlyticsCore).build();
    Fabric.with(this, crashlytics);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    Timber.plant(new CrashlyticsTree());
  }

  public <T> T inject(final T t) {
    return bullet.inject(t);
  }

  private void initializeRxErrorHandler() {
    RxJavaHooks.setOnError(generalErrorHelper.getGeneralErrorAction());
  }
}
