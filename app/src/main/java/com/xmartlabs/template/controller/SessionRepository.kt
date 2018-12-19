package com.xmartlabs.template.controller

import com.xmartlabs.bigbang.core.repository.CoreSessionRepository
import com.xmartlabs.bigbang.core.repository.SharedPreferencesSource
import com.xmartlabs.template.model.Session
import javax.inject.Inject

class SessionRepository @Inject constructor(sharedPreferencesSource: SharedPreferencesSource)
  : CoreSessionRepository(sharedPreferencesSource) {
  override fun getSessionType() = Session::class.java

  val session
    get() = abstractSession as? Session?
}
