package com.xmartlabs.template

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.di.AppInjector
import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper
import com.xmartlabs.bigbang.core.log.LoggerTree
import com.xmartlabs.bigbang.log.crashlytics.CrashlyticsLogger
import com.xmartlabs.bigbang.retrofit.helper.ServiceErrorHandler
import com.xmartlabs.template.model.BuildInfo
import com.xmartlabs.template.module.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class BaseProjectApplication : Application(), HasActivityInjector {
  companion object {
    @JvmStatic
    lateinit var context: BaseProjectApplication
      private set
  }

  @Inject
  internal lateinit var buildInfo: BuildInfo
  @Inject
  internal lateinit var generalErrorHelper: GeneralErrorHelper
  @Inject
  internal lateinit var loggerTree: LoggerTree
  @Inject
  internal lateinit var serviceErrorHandler: ServiceErrorHandler
  @Inject
  internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  init {
    context = this
  }

  override fun onCreate() {
    super.onCreate()
    initializeThreeTenABP()
    initializeInjections()
    initializeDataBase()
    initializeRxErrorHandler()
    initializeLogging() // Crashlytics initialization should go at the end.
  }

  override fun activityInjector() = dispatchingAndroidInjector

  private fun initializeInjections() {
    createComponent().inject(this)
    AppInjector.init(this)
  }

  protected open fun createComponent() = DaggerApplicationComponent.builder()
      .application(this)
      .buildInfo(BuildInfo())
      .build()

  private fun initializeDataBase() = FlowManager.init(FlowConfig.Builder(this).build())

  private fun initializeThreeTenABP() = AndroidThreeTen.init(this)

  private fun initializeLogging() {
    loggerTree.addLogger(CrashlyticsLogger().initialize(buildInfo, this))
    Timber.plant(loggerTree)
  }

  private fun initializeRxErrorHandler() {
    serviceErrorHandler.handleServiceErrors()

    val config = TraceurConfig(
        true,
        if (BuildConfig.DEBUG) Traceur.AssemblyLogLevel.SHOW_ALL else Traceur.AssemblyLogLevel.NONE,
        generalErrorHelper.generalErrorAction)
    Traceur.enableLogging(config)
  }
}
