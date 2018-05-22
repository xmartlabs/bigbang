package com.xmartlabs.template.module

import android.app.Application
import com.xmartlabs.bigbang.core.module.AndroidModule
import com.xmartlabs.bigbang.core.module.GsonModule
import com.xmartlabs.bigbang.core.module.OkHttpModule
import com.xmartlabs.bigbang.core.module.PicassoModule
import com.xmartlabs.bigbang.retrofit.module.RestServiceModule
import com.xmartlabs.bigbang.retrofit.module.ServiceGsonModule
import com.xmartlabs.template.BaseProjectApplication
import com.xmartlabs.template.model.BuildInfo
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      ActivityModule::class,
      AndroidInjectionModule::class,
      AndroidModule::class,
      AndroidModule::class,
      ActivityModule::class,
      AppModule::class,
      ControllerModule::class,
      GsonModule::class,
      ServiceGsonModule::class,
      OkHttpModule::class,
      PicassoModule::class,
      RestServiceModule::class]
)
interface ApplicationComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    @BindsInstance
    fun buildInfo(buildInfo: BuildInfo): Builder

    fun build(): ApplicationComponent
  }

  fun inject(baseProjectApplication: BaseProjectApplication)
}
