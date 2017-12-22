package com.xmartlabs.bigbang.core.helper.gsonadapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.xmartlabs.bigbang.core.extensions.ifException
import com.xmartlabs.bigbang.core.extensions.localDatefromEpochMilli
import com.xmartlabs.bigbang.core.extensions.toInstant
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.lang.reflect.Type

class MillisecondsLocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Synchronized
  override fun serialize(date: LocalDate?, type: Type?, jsonSerializationContext: JsonSerializationContext?) =
      date?.toInstant()
          ?.toEpochMilli()
          .ifException(::JsonPrimitive) { Timber.e(it, "Date cannot be serialized, date='%s'", date) }
  
  @Synchronized
  override fun deserialize(jsonElement: JsonElement?, type: Type?,
                           jsonDeserializationContext: JsonDeserializationContext?) =
      jsonElement
          ?.let(JsonElement::toString)
          ?.takeIf(String::isNotEmpty)
          ?.let(String::toLong)
          ?.ifException(::localDatefromEpochMilli) {
            Timber.e(it, "Date cannot be parsed, date='%s'", jsonElement)
          }
}
