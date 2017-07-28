package com.xmartlabs.bigbang.core.helper.gsonadapters

import com.google.gson.*
import com.xmartlabs.bigbang.core.extensions.ifException
import com.xmartlabs.bigbang.core.extensions.localDatefromEpochMilli
import com.xmartlabs.bigbang.core.extensions.toInstant
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.lang.reflect.Type

/** [Gson] type adapter that serializes [LocalDate] objects to any specified format.  */
class LocalDateCustomFormatterAdapter(private val dateFormat: DateTimeFormatter) :
    JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  @Synchronized
  override fun serialize(date: LocalDate?, type: Type?, jsonSerializationContext: JsonSerializationContext?) =
      date?.toInstant()
          ?.let(dateFormat::format)
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
