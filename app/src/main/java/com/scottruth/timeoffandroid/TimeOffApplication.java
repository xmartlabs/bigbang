package com.scottruth.timeoffandroid;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.scottruth.timeoffandroid.helper.GeneralErrorHelper;
import com.scottruth.timeoffandroid.module.AndroidModule;
import com.scottruth.timeoffandroid.module.ControllerModule;
import com.scottruth.timeoffandroid.module.GeneralErrorHelperModule;
import com.scottruth.timeoffandroid.module.ReceiverModule;
import com.scottruth.timeoffandroid.module.RestServiceModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by remer on 08/12/2015.
 */
public class TimeOffApplication extends Application {
    private static TimeOffApplication instance;

    private BulletApplicationComponent bullet;

    public TimeOffApplication() {
        super();
        instance = this;
    }

    public static TimeOffApplication getContext() {
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
//                .databaseModule(new DatabaseModule())
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
