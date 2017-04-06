package com.xmartlabs.base.core;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bullet.ObjectGraph;

public class Injector {
  private static Injector instance = new Injector();

  private ObjectGraph bullet;

  public static <T> void inject(@NonNull T t) {
    if (instance.bullet == null) {
      throw new IllegalStateException("No ObjectGraph is present. Did you forgot to call setObjectGraph?");
    }
    instance.bullet.inject(t);
  }

  public static Injector getComponent() {
    return instance;
  }

  public void setObjectGraph(@NonNull ObjectGraph bullet) {
    this.bullet = bullet;
  }
}
