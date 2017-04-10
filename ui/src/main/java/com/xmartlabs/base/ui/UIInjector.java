package com.xmartlabs.base.ui;

import android.support.annotation.NonNull;

import bullet.ObjectGraph;

final public class UIInjector {
  private static UIInjector instance = new UIInjector();

  private UIInjector(){}

  private ObjectGraph bullet;

  public static <T> void inject(@NonNull T t) {
    if (instance.bullet == null) {
      throw new IllegalStateException("No ObjectGraph is present. Did you forgot to call setObjectGraph?");
    }
    instance.bullet.inject(t);
  }

  public static UIInjector getInstance() {
    return instance;
  }

  public void setObjectGraph(@NonNull ObjectGraph bullet) {
    this.bullet = bullet;
  }
}
