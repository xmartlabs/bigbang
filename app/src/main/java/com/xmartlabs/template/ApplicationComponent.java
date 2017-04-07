package com.xmartlabs.template;

import com.xmartlabs.base.core.CoreComponent;
import com.xmartlabs.base.core.module.CoreAndroidModule;
import com.xmartlabs.base.core.module.GsonModule;
import com.xmartlabs.base.core.module.LoggerModule;
import com.xmartlabs.base.core.module.OkHttpModule;
import com.xmartlabs.base.core.module.PicassoModule;
import com.xmartlabs.base.core.module.RestServiceModule;
import com.xmartlabs.template.controller.Controller;
import com.xmartlabs.template.controller.ServiceController;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.ui.BaseActivity;
import com.xmartlabs.template.ui.BaseAppCompatActivity;
import com.xmartlabs.template.ui.BaseFragment;
import com.xmartlabs.template.ui.MainActivity;
import com.xmartlabs.template.ui.SingleFragmentActivity;
import com.xmartlabs.template.ui.StartActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    CoreAndroidModule.class,
    ControllerModule.class,
    LoggerModule.class,
    GsonModule.class,
    OkHttpModule.class,
    PicassoModule.class,
    RestServiceModule.class,
})
public interface ApplicationComponent extends CoreComponent {
  void inject(BaseProjectApplication baseProjectApplication);

  void inject(BaseActivity baseActivity);
  void inject(BaseAppCompatActivity baseAppCompatActivity);
  void inject(SingleFragmentActivity singleFragmentActivity);

  void inject(MainActivity mainActivity);
  void inject(StartActivity startActivity);

  void inject(BaseFragment baseFragment);

  void inject(Controller controller);
  void inject(ServiceController serviceController);
  void inject(SessionController sessionController);
}
