package com.xmartlabs.template

import android.app.Application
import bullet.ObjectGraph
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
import timber.log.Timber
import javax.inject.Inject

open class BaseProjectApplication : Application() {
  companion object {
    @JvmStatic val context = this
  }

  @Inject
  internal lateinit var buildInfo: BuildInfo
  @Inject
  internal lateinit var generalErrorHelper: GeneralErrorHelper
  @Inject
  internal lateinit var loggerTree: LoggerTree
  @Inject
  internal lateinit var serviceErrorHandler: ServiceErrorHandler
  
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
    val bullet = createBullet(component)
    Injector.instance.bullet = bullet
    Injector.inject(this)
  }

  protected open fun createComponent() = DaggerApplicationComponent.builder()
      .coreAndroidModule(AndroidModule(this))
      .build()

  protected open fun createBullet(component: ApplicationComponent): ObjectGraph = BulletApplicationComponent(component)

  private fun initializeDataBase() = FlowManager.init(FlowConfig.Builder(this).build())

  private fun initializeThreeTenABP() = AndroidThreeTen.init(this)

  private fun initializeLogging() {
    loggerTree.addLogger(CrashlyticsLogger().initialize(buildInfo, this))
    Timber.plant(loggerTree)
  }

  fun <T> inject(t: T) = Injector.inject(t)

  private fun initializeRxErrorHandler() {
    serviceErrorHandler.handleServiceErrors()

    val config = TraceurConfig(
        true,
        if (BuildConfig.DEBUG) Traceur.AssemblyLogLevel.SHOW_ALL else Traceur.AssemblyLogLevel.NONE,
        generalErrorHelper.generalErrorAction)
    Traceur.enableLogging(config)
  }
}
