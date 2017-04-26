package com.xmartlabs.bigbang.retrofit.common;

import com.annimon.stream.Objects;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

/** Converts {@link LocalDate} and {@link LocalDateTime} to Strings */
public class ServiceStringConverter extends Converter.Factory {
  @Override
  public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    if (type instanceof Class) {
      if (Objects.equals(type, LocalDate.class)) {
        return value -> String.valueOf(((LocalDate) value).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
      } else if (Objects.equals(type, LocalDateTime.class)) {
        return value -> String.valueOf(((LocalDateTime) value).toInstant(ZoneOffset.UTC).toEpochMilli());
      }
    }
    return super.stringConverter(type, annotations, retrofit);
  }
}
