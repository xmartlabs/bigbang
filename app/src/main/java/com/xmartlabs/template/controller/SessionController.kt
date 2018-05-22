package com.xmartlabs.template.controller

import com.xmartlabs.bigbang.core.controller.CoreSessionController
import com.xmartlabs.bigbang.core.controller.SharedPreferencesController
import com.xmartlabs.template.model.Session
import javax.inject.Inject

class SessionController @Inject constructor(sharedPreferencesController: SharedPreferencesController)
  : CoreSessionController(sharedPreferencesController) {
  override fun getSessionType() = Session::class.java

  val session
    get() = abstractSession as? Session?
}
