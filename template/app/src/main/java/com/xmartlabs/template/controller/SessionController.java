package com.xmartlabs.template.controller;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.xmartlabs.bigbang.core.controller.CoreSessionController;
import com.xmartlabs.bigbang.core.model.SessionType;
import com.xmartlabs.template.model.Session;

public class SessionController extends CoreSessionController {
  public SessionController(Class<? extends SessionType> concreteSessionType) {
    super(concreteSessionType);
  }

  @NonNull
  public Optional<Session> getSession() {
    return super.getSession()
        .map(session -> (Session) session);
  }
}
