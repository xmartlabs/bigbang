package com.xmartlabs.template.controller;

import com.xmartlabs.template.BaseProjectApplication;

public abstract class Controller {
  public Controller() {
    BaseProjectApplication.getContext().inject(this);
  }
}
