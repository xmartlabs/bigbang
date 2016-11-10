package com.xmartlabs.template.service.common;

import com.annimon.stream.Stream;
import com.xmartlabs.template.helper.ObjectHelper;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by mirland on 10/11/16.
 */
public class ServiceStringConverter extends Converter.Factory {
  @Override
  public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    if (type instanceof Class) {
      if (ObjectHelper.areSameClass((Class) type, LocalDate.class)) {
        return value -> String.valueOf(((LocalDate) value).atStartOfDay(ZoneOffset.UTC).toEpochSecond());
      } else if (ObjectHelper.areSameClass((Class) type, LocalDateTime.class)) {
        return value -> String.valueOf(((LocalDateTime) value).atZone(ZoneOffset.UTC).toEpochSecond());
      }
    }
    return super.stringConverter(type, annotations, retrofit);
  }
}
