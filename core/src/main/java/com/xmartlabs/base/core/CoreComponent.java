package com.xmartlabs.base.core;

import com.xmartlabs.base.core.module.AndroidModule;
import com.xmartlabs.base.core.module.GsonModule;
import com.xmartlabs.base.core.module.LoggerModule;
import com.xmartlabs.base.core.module.OkHttpModule;
import com.xmartlabs.base.core.module.PicassoModule;
import com.xmartlabs.base.core.module.RestServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AndroidModule.class,
    LoggerModule.class,
    GsonModule.class,
    OkHttpModule.class,
    PicassoModule.class,
    RestServiceModule.class,
})
public interface CoreComponent {

}
