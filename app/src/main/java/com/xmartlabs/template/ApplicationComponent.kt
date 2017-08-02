package com.xmartlabs.template

import com.xmartlabs.bigbang.core.controller.Controller
import com.xmartlabs.bigbang.core.controller.SessionController
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController
import com.xmartlabs.bigbang.core.module.CoreAndroidModule
import com.xmartlabs.bigbang.core.module.GsonModule
import com.xmartlabs.bigbang.core.module.OkHttpModule
import com.xmartlabs.bigbang.core.module.PicassoModule
import com.xmartlabs.bigbang.retrofit.module.RestServiceModule
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule
import com.xmartlabs.bigbang.ui.BaseActivity
import com.xmartlabs.bigbang.ui.BaseAppCompatActivity
import com.xmartlabs.bigbang.ui.SingleFragmentActivity
import com.xmartlabs.template.module.ControllerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    CoreAndroidModule::class,
    ControllerModule::class,
    GsonModule::class,
    ServiceGsonModule::class,
    OkHttpModule::class,
    PicassoModule::class,
    RestServiceModule::class)
)
interface ApplicationComponent {
  fun inject(baseProjectApplication: BaseProjectApplication)

  fun inject(baseActivity: BaseActivity)
  fun inject(baseAppCompatActivity: BaseAppCompatActivity)
  fun inject(singleFragmentActivity: SingleFragmentActivity)
  fun inject(startActivity: StartActivity)
  
  fun inject(controller: Controller)
  fun inject(sessionController: SessionController)

  fun inject(sharedPreferencesController: SharedPreferencesController)
}
