package com.xmartlabs.template.controller;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

public class SessionController extends com.xmartlabs.bigbang.core.controller.SessionController<Session> {
  @Inject
  Gson gson;

  @Override
  protected Optional<Session> deserializeSession(String json) {
    return Exceptional.of(() -> gson.fromJson(json, Session.class))
        .getOptional();
  }
}
