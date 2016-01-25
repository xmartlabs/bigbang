package com.xmartlabs.template.controller;

import com.xmartlabs.template.BaseProjectApplication;

/**
 * Created by santiago on 17/09/15.
 */
public abstract class Controller {
  public Controller() {
    BaseProjectApplication.getContext().inject(this);
  }
}
