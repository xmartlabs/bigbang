package com.xmartlabs.template;

import com.xmartlabs.bigbang.core.controller.Controller;
import com.xmartlabs.bigbang.core.controller.CoreSessionController;
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController;
import com.xmartlabs.bigbang.core.module.CoreAndroidModule;
import com.xmartlabs.bigbang.core.module.GsonModule;
import com.xmartlabs.bigbang.core.module.OkHttpModule;
import com.xmartlabs.bigbang.core.module.PicassoModule;
import com.xmartlabs.bigbang.retrofit.module.RestServiceModule;
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule;
import com.xmartlabs.bigbang.ui.BaseActivity;
import com.xmartlabs.bigbang.ui.BaseAppCompatActivity;
import com.xmartlabs.bigbang.ui.BaseFragment;
import com.xmartlabs.bigbang.ui.SingleFragmentActivity;
import com.xmartlabs.template.module.ControllerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    CoreAndroidModule.class,
    ControllerModule.class,
    GsonModule.class,
    ServiceGsonModule.class,
    OkHttpModule.class,
    PicassoModule.class,
    RestServiceModule.class,
})
public interface ApplicationComponent {
  void inject(BaseProjectApplication baseProjectApplication);

  void inject(BaseActivity baseActivity);
  void inject(BaseAppCompatActivity baseAppCompatActivity);
  void inject(SingleFragmentActivity singleFragmentActivity);

  void inject(MainActivity mainActivity);
  void inject(StartActivity startActivity);

  void inject(BaseFragment baseFragment);

  void inject(Controller controller);
  void inject(CoreSessionController sessionController);

  void inject(SharedPreferencesController sharedPreferencesController);
}
