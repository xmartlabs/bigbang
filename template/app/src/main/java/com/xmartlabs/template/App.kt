package com.xmartlabs.template

import android.app.Application
import android.content.Context
import android.os.Build
import android.support.multidex.MultiDex
import com.jakewharton.threetenabp.AndroidThreeTen
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.Injector
import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper
import com.xmartlabs.bigbang.core.log.LoggerTree
import com.xmartlabs.bigbang.core.model.BuildInfo
import com.xmartlabs.bigbang.log.crashlytics.CrashlyticsLogger
import com.xmartlabs.bigbang.retrofit.helper.ServiceErrorHandler
import com.xmartlabs.template.module.AndroidModule
import com.xmartlabs.template.module.OkHttpModule
import com.xmartlabs.template.module.RestServiceModule
import com.xmartlabs.template.module.ServiceGsonModule
import timber.log.Timber
import javax.inject.Inject

class App : Application() {
  companion object {
    @Suppress("LateinitUsage")
    @JvmStatic lateinit var context: App
  }

  @Inject
  internal lateinit var buildInfo: BuildInfo
  @Inject
  internal lateinit var generalErrorHelper: GeneralErrorHelper
  @Inject
  internal lateinit var loggerTree: LoggerTree
  @Inject
  internal lateinit var serviceErrorHandler: ServiceErrorHandler

  init {
    context = this
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || !BuildConfig.DEBUG) {
      MultiDex.install(this)
    }
  }

  override fun onCreate() {
    super.onCreate()
    initializeThreeTenABP()
    initializeInjections()
    initializeDataBase()
    initializeRxErrorHandler()
    initializeLogging() // Crashlytics initialization should go at the end.
  }

  private fun initializeInjections() {
    val component = createComponent()
    Injector.instance.bullet = createBullet(component)
    Injector.inject(this)
  }

  private fun createComponent(): ApplicationComponent = DaggerApplicationComponent.builder()
      .coreAndroidModule(AndroidModule(this))
      .restServiceModule(RestServiceModule())
      .okHttpModule(OkHttpModule())
      .serviceGsonModule(ServiceGsonModule())
      .build()

  private fun createBullet(component: ApplicationComponent) = BulletApplicationComponent(component)

  private fun initializeDataBase() = FlowManager.init(FlowConfig.Builder(this).build())

  private fun initializeThreeTenABP() = AndroidThreeTen.init(this)

  private fun initializeLogging() {
    //TODO: Configure Fabric and add Fabric apiSecret and apiKey properties file in the root folder
    loggerTree.addLogger(CrashlyticsLogger().initialize(buildInfo, this))
    Timber.plant(loggerTree)
  }

  private fun initializeRxErrorHandler() {
    serviceErrorHandler.handleServiceErrors()

    val level = if (BuildConfig.DEBUG) Traceur.AssemblyLogLevel.SHOW_ALL else Traceur.AssemblyLogLevel.NONE
    val config = TraceurConfig(true, level) { generalErrorHelper.generalErrorAction(it) }
    Traceur.enableLogging(config)
  }
}
