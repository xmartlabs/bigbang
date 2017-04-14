package com.xmartlabs.base.core.controller;

import com.xmartlabs.base.core.Injector;

public abstract class Controller {
  public Controller() {
    Injector.inject(this);
  }
}
