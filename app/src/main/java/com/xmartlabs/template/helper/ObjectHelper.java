package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by medina on 16/09/2016.
 */
@SuppressWarnings("unused")
@Singleton
public class ObjectHelper {
  private final Gson gson;

  @Inject
  public ObjectHelper(Gson gson) {
    this.gson = gson;
  }

  /**
   * Generates a random UUID using the <code>UUID</code> class.
   *
   * @return the string form of the generated UUID
   */
  public String generateUUID() {
    return UUID.randomUUID().toString();
  }

  /**
   * Uses <code>Gson</code> to perform a deep copy of the <code>object</code>, which should result in an object of type
   * <code>resultClass</code>.
   *
   * @param object the object to copy.
   * @param resultClass the resulting class type of the copied object.
   * @param <T> the type of the resulting copied object.
   * @return a new instance of type T copied from <code>object</code>.
   */
  public <T> T deepCopy(Object object, @NonNull Class<T> resultClass) {
    String json = gson.toJson(object);
    return gson.fromJson(json, resultClass);
  }

  /**
   * Compares the two given ints, <code>x</code> and <code>y</code>, yielding the following result:
   * <ul>
   *   <li>-1 if <code>x</code> &lt; <code>y</code></li>
   *   <li>0 if <code>x</code> == <code>y</code></li>
   *   <li>1 if <code>x</code> > <code>y</code></li>
   * </ul>
   *
   * @param x the first int to compare.
   * @param y the second int to compare.
   * @return the difference between <code>x</code> and <code>y</code> as explained above.
   */
  public static int compare(int x, int y) {
    return (x < y) ? -1 : ((x == y) ? 0 : 1);
  }
}

