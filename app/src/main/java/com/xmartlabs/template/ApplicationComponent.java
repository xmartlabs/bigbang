package com.xmartlabs.template;

import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.controller.Controller;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.controller.SharedPreferencesController;
import com.xmartlabs.template.helper.DatabaseHelper;
import com.xmartlabs.template.helper.GeneralErrorHelper;
import com.xmartlabs.template.module.AndroidModule;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.module.DatabaseModule;
import com.xmartlabs.template.module.GsonModule;
import com.xmartlabs.template.module.LoggerModule;
import com.xmartlabs.template.module.OkHttpModule;
import com.xmartlabs.template.module.PicassoModule;
import com.xmartlabs.template.module.ReceiverModule;
import com.xmartlabs.template.module.RestServiceModule;
import com.xmartlabs.template.module.SessionInterceptor;
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
    AndroidModule.class,
    ControllerModule.class,
    DatabaseModule.class,
    LoggerModule.class,
    GsonModule.class,
    OkHttpModule.class,
    PicassoModule.class,
    ReceiverModule.class,
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

  void inject(AuthController authController);
  void inject(SessionController sessionController);
  void inject(SharedPreferencesController sharedPreferencesController);

  void inject(SessionInterceptor sessionInterceptor);

  void inject(DatabaseHelper databaseHelper);
  void inject(GeneralErrorHelper generalErrorHelper);
}
