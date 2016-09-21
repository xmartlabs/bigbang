package com.xmartlabs.template;

import com.xmartlabs.template.module.AndroidModule;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.module.DatabaseModule;
import com.xmartlabs.template.module.GeneralErrorHelperModule;
import com.xmartlabs.template.module.GsonModule;
import com.xmartlabs.template.module.MockRestServiceModule;
import com.xmartlabs.template.module.OkHttpModule;
import com.xmartlabs.template.module.PicassoModule;
import com.xmartlabs.template.ui.common.BaseInstrumentationTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by medina on 21/09/2016.
 */
@Component(modules = {
    AndroidModule.class,
    ControllerModule.class,
    DatabaseModule.class,
    GeneralErrorHelperModule.class,
    GsonModule.class,
    MockRestServiceModule.class,
    OkHttpModule.class,
    PicassoModule.class,
})
@Singleton
public interface TestComponent extends ApplicationComponent {
  void inject(BaseInstrumentationTest baseInstrumentationTest);

  void inject(TestRunner testRunner);
}
