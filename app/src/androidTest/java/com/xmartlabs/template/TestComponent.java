package com.xmartlabs.template;

import com.xmartlabs.bigbang.core.module.CoreAndroidModule;
import com.xmartlabs.bigbang.core.module.GsonModule;
import com.xmartlabs.bigbang.core.module.OkHttpModule;
import com.xmartlabs.bigbang.core.module.PicassoModule;
import com.xmartlabs.bigbang.retrofit.module.RestServiceModule;
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.ui.common.BaseInstrumentationTest;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
    CoreAndroidModule.class,
    ControllerModule.class,
    GsonModule.class,
    ServiceGsonModule.class,
    RestServiceModule.class,
    OkHttpModule.class,
    PicassoModule.class,
})
@Singleton
public interface TestComponent extends ApplicationComponent {
  void inject(BaseInstrumentationTest baseInstrumentationTest);

  void inject(TestRunner testRunner);
}
