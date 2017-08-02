package com.xmartlabs.bigbang.retrofit.common

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/** Converts [LocalDate] and [LocalDateTime] to Strings  */
class ServiceStringConverter : Converter.Factory() {
  override fun stringConverter(type: Type?, annotations: Array<Annotation>?,
                               retrofit: Retrofit?): Converter<*, String> {
    return when (type) {
      is Class<*> -> when (type) {
        LocalDate::class.java -> Converter { value: LocalDate -> value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli().toString() }
        LocalDateTime::class.java -> Converter { value: LocalDateTime -> value.toInstant(ZoneOffset.UTC).toEpochMilli().toString() }
        else -> super.stringConverter(type, annotations, retrofit)
      }
      else -> super.stringConverter(type, annotations, retrofit)
    }
  }
}
