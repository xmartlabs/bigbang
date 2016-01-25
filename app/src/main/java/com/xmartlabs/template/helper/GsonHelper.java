package com.xmartlabs.template.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.template.module.GsonUtcDateAdapter;

import java.util.Date;

/**
 * Created by santiago on 23/10/15.
 */
public class GsonHelper {
  private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(Date.class, new GsonUtcDateAdapter())
      .create();

  public static Gson getGson() {
    return GSON;
  }
}
