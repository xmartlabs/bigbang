package com.xmartlabs.template.controller;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.xmartlabs.bigbang.core.model.SessionType;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

public class SessionController extends com.xmartlabs.bigbang.core.controller.SessionController {
  @Inject
  Gson gson;

  @Override
  protected Optional<SessionType> deserializeSession(String json) {
    return Exceptional.of(() -> (SessionType) gson.fromJson(json, Session.class))
        .getOptional();
  }

  public Optional<Session> getSession() {
    return getAbstractSession()
        .map(session -> (Session) session);
  }
}
