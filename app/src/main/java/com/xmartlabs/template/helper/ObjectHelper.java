package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by medina on 16/09/2016.
 */
@Singleton
public class ObjectHelper {
  private final Gson gson;

  @Inject
  public ObjectHelper(Gson gson) {
    this.gson = gson;
  }

  public String generateUUID() {
    return UUID.randomUUID().toString();
  }

  public <T> T deepCopy(Object object, @NonNull Class<T> resultClass) {
    String json = gson.toJson(object);
    return gson.fromJson(json, resultClass);
  }

  public static int compare(int x, int y) {
    return (x < y) ? -1 : ((x == y) ? 0 : 1);
  }
}

