package com.xmartlabs.bigbang.core.controller;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.annimon.stream.Exceptional;
import com.annimon.stream.Objects;
import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Manage the {@link SharedPreferences} storage of the application.
 *
 * The entities will be stored in the {@link SharedPreferences} as a String.
 * Thus, the entities objects are serialized using {@link Gson}.
 * The entities are retrieved once from disk and then kept in memory for faster access.
 */
public class SharedPreferencesController extends Controller {
  private final Gson gson;
  private final SharedPreferences sharedPreferences;

  @Inject
  public SharedPreferencesController(@NonNull Gson gson, @NonNull SharedPreferences sharedPreferences) {
    this.gson = gson;
    this.sharedPreferences = sharedPreferences;
  }

  private final Map<String, Object> cachedEntities = new HashMap<>();

  /**
   * Retrieves the current stored {@link T} entity, if it exists.
   *
   * Only upon first request the {@link T} entity object will be queried from {@link SharedPreferences}.
   * Then, it will be stored in memory for faster access.
   *
   * @param key  the key of the entity.
   * @param type the class type of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  public <T> Optional<T> getEntity(String key, Class<T> type) {
    return getEntityFromCachedElements(key, type)
        .or(() -> getEntityFromSharedPreferences(key, type));
  }

  /**
   * Retrieves the current stored {@link T} entity, if it exists.
   *
   * Only upon first request the {@link T} entity object will be queried from {@link SharedPreferences}.
   * Then, it will be stored in memory for faster access.
   *
   * @param key  the key of the entity.
   * @param type the class type of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  public <T> Optional<T> getEntity(String key, TypeToken<T> type) {
    //noinspection unchecked
    return getEntityFromCachedElements(key)
        .map(cachedElement -> (T) cachedElement)
        .or(() -> getEntityFromSharedPreferences(key, type));
  }

  /**
   * Retrieves the current stored {@link T} entity, from the cache.
   *
   * @param key  the key of the entity.
   * @param type the class type of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  private <T> Optional<T> getEntityFromCachedElements(String key, Class<T> type) {
    //noinspection unchecked
    return Optional.ofNullable(cachedEntities.get(key))
        .filter(element -> Objects.equals(element.getClass(), type))
        .map(element -> (T) element);
  }

  /**
   * Retrieves the current stored {@link T} entity, from the cache.
   *
   * @param key  the key of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  @SuppressWarnings("WeakerAccess")
  protected <T> Optional<T> getEntityFromCachedElements(String key) {
    //noinspection unchecked
    return Optional.ofNullable(cachedEntities.get(key))
        .map(element -> (T) element);
  }

  /**
   * Retrieves the current stored {@link T} entity, from the {@link SharedPreferences}.
   *
   * @param key  the key of the entity.
   * @param type the class type of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  private <T> Optional<T> getEntityFromSharedPreferences(String key, Class<T> type) {
    return Optional.ofNullable(sharedPreferences.getString(key, null))
        .flatMap(entity ->
            Exceptional.of(() -> gson.fromJson(entity, type))
                .ifException(throwable -> Timber.e(throwable, "Deserialization was not correct, entity= %s", entity))
                .getOptional()
        )
        .executeIfPresent(entity -> cachedEntities.put(key, entity));
  }

  /**
   * Retrieves the current stored {@link T} entity, from the {@link SharedPreferences}.
   *
   * @param key  the key of the entity.
   * @param type the class type of the entity.
   * @param <T> the type of the entity.
   * @return the current {@link T} entity if exists.
   */
  @CheckResult
  @NonNull
  private <T> Optional<T> getEntityFromSharedPreferences(String key, TypeToken<T> type) {
    return Optional.ofNullable(sharedPreferences.getString(key, null))
        .flatMap(entity ->
            Exceptional.of(() -> gson.<T>fromJson(entity, type.getType()))
                .ifException(throwable -> Timber.e(throwable, "Deserialization was not correct, entity= %s", entity))
                .getOptional()
        )
        .executeIfPresent(entity -> cachedEntities.put(key, entity));
  }

  /**
   * Stores the {@code T} entity into the {@link SharedPreferences}.
   *
   * @param key the key to be stored.
   * @param value the {@link T} entity to be stored.
   * @param <T> the type of the entity to be stored.
   * @return the {@code Single<T>} object. Upon subscription, it will only fail if the session could not be stored.
   */
  @SuppressLint("ApplySharedPref")
  public <T> Single<T> saveEntity(String key, T value) {
    return Single.fromCallable(() -> {
      String serializedValue = gson.toJson(value);
      cachedEntities.put(key, value);

      sharedPreferences.edit()
          .putString(key, serializedValue)
          .commit();
      return value;
    }).compose(applySingleIoSchedulers());
  }

  /**
   * Deletes the entity with the associated {@code key}.
   *
   * @param key the key of the entity to be removed.
   */
  public void deleteEntity(String key) {
    cachedEntities.remove(key);
    sharedPreferences
        .edit()
        .remove(key)
        .apply();
  }

  /**
   * Check if the entity with the associated {@code key} exists.
   *
   * @param key the key of the entity.
   */
  @CheckResult
  public boolean hasEntity(String key) {
    return cachedEntities.containsKey(key) || sharedPreferences.contains(key);
  }
}
