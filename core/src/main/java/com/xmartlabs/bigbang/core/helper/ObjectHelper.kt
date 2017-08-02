package com.xmartlabs.bigbang.core.helper

import com.google.gson.Gson
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectHelper @Inject
constructor(val gson: Gson) {
  companion object {
    /**
     * Generates a random UUID using the [UUID] class.
     * @return the string form of the generated UUID
     */
    @JvmStatic
    fun generateUUID() = UUID.randomUUID().toString()
    
    /**
     * Compares the two given ints, `x` and `y`, yielding the following result:
     *
     *  * -1 if `x` &lt; `y`
     *  * 0 if `x` == `y`
     *  * 1 if `x` &gt; `y`
     *
     * @param x the first int to compare.
     * *
     * @param y the second int to compare.
     * *
     * @return the difference between `x` and `y` as explained above.
     */
    @JvmStatic
    fun compare(x: Int, y: Int) = if (x < y) -1 else if (x == y) 0 else 1
  }

  /**
   * Uses [Gson] to perform a deep copy of the `object`, which should result in an object of type
   * `resultClass`.
   * @param object the object to copy.
   * *
   * @param <T> the type of the resulting copied object.
   * *
   * @return a new instance of type T copied from `object`.
  </T> */
  inline fun <reified T> deepCopy(`object`: Any): T {
    val json = gson.toJson(`object`)
    return gson.fromJson(json, T::class.java)
  }
}

