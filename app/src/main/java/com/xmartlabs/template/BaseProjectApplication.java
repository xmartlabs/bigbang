package com.xmartlabs.template;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.xmartlabs.template.module.AndroidModule;
import com.xmartlabs.template.module.GeneralErrorHelperModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by remer on 08/12/15.
 */
public class BaseProjectApplication extends Application {
  private static BaseProjectApplication instance;

  private BulletApplicationComponent bullet;

  public BaseProjectApplication() {
    instance = this;
  }

  public static BaseProjectApplication getContext() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
        .disabled(BuildConfig.DEBUG)
        .build();
    Crashlytics crashlytics = new Crashlytics.Builder().core(crashlyticsCore).build();
    Fabric.with(this, crashlytics);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    Timber.plant(new CrashlyticsTree());

    ApplicationComponent component = DaggerApplicationComponent.builder()
        .androidModule(new AndroidModule(this))
        .generalErrorHelperModule(new GeneralErrorHelperModule())
        .build();
    bullet = new BulletApplicationComponent(component);
  }

  public <T> T inject(final T t) {
    return bullet.inject(t);
  }
}
