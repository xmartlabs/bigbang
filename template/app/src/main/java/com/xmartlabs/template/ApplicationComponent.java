package com.xmartlabs.template;

import com.xmartlabs.bigbang.core.controller.SessionController;
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController;
import com.xmartlabs.bigbang.core.module.CoreAndroidModule;
import com.xmartlabs.bigbang.core.module.GsonModule;
import com.xmartlabs.bigbang.core.module.OkHttpModule;
import com.xmartlabs.bigbang.core.module.PicassoModule;
import com.xmartlabs.bigbang.core.module.SessionInterceptor;
import com.xmartlabs.bigbang.core.providers.AccessTokenProvider;
import com.xmartlabs.bigbang.retrofit.module.RestServiceModule;
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule;
import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.module.ControllerModule;
import com.xmartlabs.template.module.RestServiceModuleApi;
import com.xmartlabs.template.ui.StartActivity;
import com.xmartlabs.template.ui.login.LoginActivity;
import com.xmartlabs.template.ui.login.LoginFragment;
import com.xmartlabs.template.ui.login.LoginPresenter;
import com.xmartlabs.template.ui.onboarding.OnboardingActivity;
import com.xmartlabs.template.ui.onboarding.OnboardingFragment;
import com.xmartlabs.template.ui.onboarding.OnboardingPresenter;
import com.xmartlabs.template.ui.onboarding.page.OnboardingPageFragment;
import com.xmartlabs.template.ui.recyclerfragmentexample.RecyclerExampleActivity;
import com.xmartlabs.template.ui.recyclerfragmentexample.RecyclerExampleFragment;
import com.xmartlabs.template.ui.recyclerfragmentexample.RecyclerExamplePresenter;

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
    RestServiceModuleApi.class,
})
public interface ApplicationComponent {
  void inject(App app);

  void inject(LoginActivity loginActivity);
  void inject(OnboardingActivity onboardingActivity);
  void inject(RecyclerExampleActivity recyclerExampleActivity);
  void inject(StartActivity startActivity);

  void inject(LoginFragment loginFragment);
  void inject(OnboardingFragment onboardingFragment);
  void inject(OnboardingPageFragment onboardingPageFragment);
  void inject(RecyclerExampleFragment recyclerExampleFragment);

  void inject(LoginPresenter loginPresenter);
  void inject(OnboardingPresenter onboardingPresenter);
  void inject(RecyclerExamplePresenter recyclerExamplePresenter);

  void inject(AuthController authController);
  void inject(SessionController sessionController);
  void inject(SharedPreferencesController sharedPreferencesController);

  void inject(AccessTokenProvider accessTokenProvider);
  void inject(SessionInterceptor sessionInterceptor);
}
