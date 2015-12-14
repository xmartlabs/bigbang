package com.scottruth.timeoffandroid.module;

import com.scottruth.timeoffandroid.controller.SessionController;
import com.scottruth.timeoffandroid.controller.demo.DemoController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by santiago on 31/08/15.
 */
@Module
public class ControllerModule {
    @Provides
    @Singleton
    public SessionController provideSessionController() {
        return new SessionController();
    }

    @Provides
    @Singleton
    public DemoController provideDemoController() {
        return new DemoController();
    }
}
