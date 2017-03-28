package com.xmartlabs.template.helper;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xmartlabs.template.service.adapter.MillisecondsLocalDateAdapter;
import com.xmartlabs.template.service.gson.GsonExclude;
import com.xmartlabs.template.service.gson.GsonRequired;
import com.xmartlabs.template.service.gson.JsonRequiredFieldException;
import com.xmartlabs.template.service.gson.RequiredFieldDeserializer;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import lombok.Builder;
import lombok.Data;

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
            return fieldAttributes.getAnnotation(GsonExclude.class) != null;
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

    Assert.assertNull(exception);
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
    Assert.assertNull(value.getExcluded());
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

    Assert.assertTrue(value.contains(serializedDate));
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
