package com.xmartlabs.base.core;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.xmartlabs.base.core.service.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.base.core.service.common.GsonExclude;
import com.xmartlabs.base.core.service.common.GsonRequired;
import com.xmartlabs.base.core.service.deserializer.RequiredFieldDeserializer;
import com.xmartlabs.base.core.service.exception.JsonRequiredFieldException;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import lombok.Builder;
import lombok.Data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class GsonRequiredTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new GsonBuilder()
        .registerTypeHierarchyAdapter(Object.class, new RequiredFieldDeserializer())
        .registerTypeAdapter(LocalDate.class, new MillisecondsLocalDateAdapter())
        .setExclusionStrategies(new ExclusionStrategy() {
          @Override
          public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(GsonExclude.class) != null
                || fieldAttributes.getDeclaredClass().equals(ModelAdapter.class);
          }

          @Override
          public boolean shouldSkipClass(Class<?> clazz) {
            return false;
          }
        })
        .create();
  }

  @Test(expected = JsonRequiredFieldException.class)
  public void testRequiredFieldNotPresent() {
    TestInnerClass innerClass = TestInnerClass.builder()
        .requiredObject("obj")
        .build();
    TestClass testClass = TestClass.builder()
        .innerRequiredObject(innerClass)
        .build();
    gson.fromJson(gson.toJson(testClass), TestClass.class);
  }

  @Test(expected = JsonRequiredFieldException.class)
  public void testRequiredFieldNotPresentInInnerObject() {
    TestInnerClass innerClass = TestInnerClass.builder()
        .build();
    TestClass testClass = TestClass.builder()
        .innerRequiredObject(innerClass)
        .requiredField("obj")
        .build();
    gson.fromJson(gson.toJson(testClass), TestClass.class);
  }

  @Test
  public void testRequiredFieldPresent() {
    TestInnerClass innerClass = TestInnerClass.builder()
        .requiredObject("obj")
        .build();
    TestClass testClass = TestClass.builder()
        .innerRequiredObject(innerClass)
        .requiredField("obj")
        .build();

    Exception exception = null;
    try {
      gson.fromJson(gson.toJson(testClass), TestClass.class);
    } catch (Exception e) {
      exception = e;
    }

    assertThat(exception, nullValue());
  }

  @Test
  public void testOtherConfigurationsStillWork() {
    TestInnerClass innerClass = TestInnerClass.builder()
        .requiredObject("obj")
        .build();
    TestClass testClass = TestClass.builder()
        .innerRequiredObject(innerClass)
        .requiredField("obj")
        .excluded("Something")
        .build();

    TestClass value = gson.fromJson(gson.toJson(testClass), TestClass.class);
    assertThat(value.getExcluded(), nullValue());
  }

  @Test
  public void testOtherTypeAdaptersAreStillWorking() {
    TestInnerClass innerClass = TestInnerClass.builder()
        .requiredObject("obj")
        .build();
    TestClass testClass = TestClass.builder()
        .innerRequiredObject(innerClass)
        .requiredField("obj")
        .excluded("Something")
        .date(LocalDate.now())
        .build();

    String value = gson.toJson(testClass);

    MillisecondsLocalDateAdapter adapter = new MillisecondsLocalDateAdapter();
    String serializedDate  = adapter.serialize(testClass.date, LocalDate.class, null).getAsString();

    assertThat(value.contains(serializedDate), is(true));
  }

  @Builder
  @Data
  private static final class TestClass {
    String field;
    @GsonRequired
    String requiredField;
    TestInnerClass innerObject;
    @GsonRequired
    TestInnerClass innerRequiredObject;
    @GsonExclude
    String excluded;
    LocalDate date;
  }

  @Builder
  private static final class TestInnerClass {
    String field;
    @GsonRequired
    String requiredObject;
  }
}
