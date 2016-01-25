package com.xmartlabs.template;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.xmartlabs.template.module.AndroidModule;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.module.DatabaseModule;
import com.xmartlabs.template.module.GeneralErrorHelperModule;
import com.xmartlabs.template.module.ReceiverModule;
import com.xmartlabs.template.module.RestServiceModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by remer on 08/12/2015.
 */
public class BaseProjectApplication extends Application {
    private static BaseProjectApplication instance;

    private BulletApplicationComponent bullet;

    public BaseProjectApplication() {
        super();
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

        GeneralErrorHelperModule generalErrorHelperModule = new GeneralErrorHelperModule();
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .controllerModule(new ControllerModule())
                .databaseModule(new DatabaseModule())
                .generalErrorHelperModule(generalErrorHelperModule)
                .receiverModule(new ReceiverModule())
                .restServiceModule(new RestServiceModule())
                .build();
        bullet = new BulletApplicationComponent(component);
    }

    public <T> T inject(final T t) {
        return bullet.inject(t);
    }
}
