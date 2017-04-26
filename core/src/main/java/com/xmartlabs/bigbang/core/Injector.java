package com.xmartlabs.bigbang.core;

import android.support.annotation.NonNull;

import bullet.ObjectGraph;

public final class Injector {
  private static Injector instance = new Injector();

  private ObjectGraph bullet;

  private Injector() {
  }

  public static <T> void inject(@NonNull T t) {
    if (instance.bullet == null) {
      throw new IllegalStateException("No ObjectGraph is present. Did you forgot to call setObjectGraph?");
    }
    instance.bullet.inject(t);
  }

  public static Injector getInstance() {
    return instance;
  }

  public void setObjectGraph(@NonNull ObjectGraph bullet) {
    this.bullet = bullet;
  }
}
