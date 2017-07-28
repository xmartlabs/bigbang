package com.xmartlabs.template.controller

import com.annimon.stream.Optional
import com.xmartlabs.bigbang.core.controller.CoreSessionController
import com.xmartlabs.bigbang.core.model.SessionType
import com.xmartlabs.template.model.Session

class SessionController(concreteSessionType: Class<out SessionType>) : CoreSessionController(concreteSessionType) {
  override fun getSession(): Optional<Session> {
    return super.getSession()
        .map { session -> session as Session }
  }
}
