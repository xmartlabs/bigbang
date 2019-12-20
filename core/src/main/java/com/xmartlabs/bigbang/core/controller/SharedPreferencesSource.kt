package com.xmartlabs.bigbang.core.controller

import android.content.SharedPreferences
import androidx.annotation.CheckResult
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Manage the [SharedPreferences] storage of the application.
 *
 * The entities will be stored in the [SharedPreferences] as a String.
 * Thus, the entities objects are serialized using [Gson].
 * The entities are retrieved once from disk and then kept in memory for faster access.
 */
open class SharedPreferencesSource @Inject
constructor(private val gson: Gson, private val sharedPreferences: SharedPreferences) {
  private val cachedEntities = HashMap<String, Any>()

  /**
   * Retrieves the current stored [T] entity, if it exists.
   *
   * Only upon first request the [T] entity object will be queried from [SharedPreferences].
   * Then, it will be stored in memory for faster access.
   *
   * @param key  the key of the entity.
   * *
   * @param type the type of the entity.
   * *
   * @return the current [T] entity if exists.
   */
  @CheckResult
  open fun <T> getEntity(key: String, type: Class<out T>) =
      getEntityFromCachedElements(key, type) ?: getEntityFromSharedPreferences(key, type)

  /**
   * Retrieves the current stored [T] entity, from the cache.
   *
   * @param key  the key of the entity.
   * *
   * @param type the type of the entity.
   * *
   * @return the current [T] entity if exists.
   */
  @CheckResult
  private fun <T> getEntityFromCachedElements(key: String, type: Class<out T>): T? {
    val element = cachedEntities[key]
    return element?.let {
      @Suppress("UNCHECKED_CAST")
      if (it::class == type::class) it as? T else null
    }
  }

  /**
   * Retrieves the current stored [T] entity, from the [SharedPreferences].
   *
   * @param key  the key of the entity.
   * *
   * @param type the type of the entity.
   * *
   * @return the current [T] entity if exists.
   */
  @CheckResult
  private fun <T> getEntityFromSharedPreferences(key: String, type: Class<out T>): T? {
    return sharedPreferences.getString(key, null)?.let {
      try { gson.fromJson(it, type) } catch (e: Exception) { null }
    }
  }

  /**
   * Stores the `T` entity into the [SharedPreferences].
   *
   * @param key   the key to be stored.
   * *
   * @param value the [T] entity to be stored.
   */
  open fun <T : Any> saveEntity(key: String, value: T) {
    val serializedValue = gson.toJson(value)
    cachedEntities.put(key, value)
    sharedPreferences.edit().putString(key, serializedValue).apply()
  }

  /**
   * Deletes an entity.
   *
   * @param key the key of the entity to be removed.
   */
  open fun deleteEntity(key: String) {
    cachedEntities.remove(key)
    sharedPreferences
        .edit()
        .remove(key)
        .apply()
  }

  /**
   * Check if the entity exists.
   *
   * @param key the key of the entity.
   */
  @CheckResult
  open fun hasEntity(key: String) = cachedEntities.containsKey(key) || sharedPreferences.contains(key)
}
