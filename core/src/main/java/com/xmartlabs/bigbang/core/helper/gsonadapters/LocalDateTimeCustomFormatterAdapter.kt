package com.xmartlabs.bigbang.core.helper.gsonadapters

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.xmartlabs.bigbang.core.extensions.ifException
import com.xmartlabs.bigbang.core.extensions.localDateTimeFromEpochMilli
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.lang.reflect.Type

/** [Gson] type adapter that serializes [LocalDate] objects to any specified format.  */
class LocalDateTimeCustomFormatterAdapter(private val dateFormat: DateTimeFormatter) :
    JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  @Synchronized
  override fun serialize(dateTime: LocalDateTime?, type: Type?, jsonSerializationContext: JsonSerializationContext?) =
      dateTime?.toInstant(ZoneOffset.UTC)
          ?.let(dateFormat::format)
          .ifException(::JsonPrimitive) { Timber.e(it, "Date cannot be serialized, date='%s'", dateTime) }

  @Synchronized
  override fun deserialize(jsonElement: JsonElement?, type: Type?,
                           jsonDeserializationContext: JsonDeserializationContext?) =
      jsonElement
          ?.let(JsonElement::getAsString)
          ?.takeIf(String::isNotEmpty)
          ?.ifException({ LocalDateTime.parse(it, dateFormat) }) {
            Timber.e(it, "Date cannot be parsed, date='%s'", jsonElement)
          }
}
