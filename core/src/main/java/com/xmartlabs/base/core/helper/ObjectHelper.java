package com.xmartlabs.base.core.helper;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("unused")
@Singleton
public class ObjectHelper {
  private final Gson gson;

  @Inject
  public ObjectHelper(Gson gson) {
    this.gson = gson;
  }

  /**
   * Generates a random UUID using the {@link UUID} class.
   *
   * @return the string form of the generated UUID
   */
  public String generateUUID() {
    return UUID.randomUUID().toString();
  }

  /**
   * Uses {@link Gson} to perform a deep copy of the {@code object}, which should result in an object of type
   * {@code resultClass}.
   *
   * @param object the object to copy.
   * @param resultClass the resulting class type of the copied object.
   * @param <T> the type of the resulting copied object.
   * @return a new instance of type T copied from {@code object}.
   */
  public <T> T deepCopy(Object object, @NonNull Class<T> resultClass) {
    String json = gson.toJson(object);
    return gson.fromJson(json, resultClass);
  }

  /**
   * Compares the two given ints, {@code x} and {@code y}, yielding the following result:
   * <ul>
   *   <li>-1 if {@code x} &lt; {@code y}</li>
   *   <li>0 if {@code x} == {@code y}</li>
   *   <li>1 if {@code x} &gt; {@code y}</li>
   * </ul>
   *
   * @param x the first int to compare.
   * @param y the second int to compare.
   * @return the difference between {@code x} and {@code y} as explained above.
   */
  public static int compare(int x, int y) {
    return (x < y) ? -1 : ((x == y) ? 0 : 1);
  }
}

