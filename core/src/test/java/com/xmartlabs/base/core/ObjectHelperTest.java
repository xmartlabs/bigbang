package com.xmartlabs.base.core;

import android.support.v4.util.Pair;

import com.annimon.stream.Objects;
import com.google.gson.Gson;
import com.xmartlabs.base.core.helper.ObjectHelper;

import org.junit.Test;

import lombok.Builder;
import lombok.Data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by mike on 11/04/2017.
 */
public class ObjectHelperTest {
  @Test
  public void deepCopy() {
    Object object = new Object();
    ObjectHelper objectHelper = new ObjectHelper(new Gson());
    assertThat(object == objectHelper.deepCopy(object, Object.class), is(false));
  }

  @Test
  public void dummyAndDummyCopyAreNotSameObject() {
    Pair<DummyObject, DummyObject> dummyAndDeepCopyOfDummy = getDummyAndDeepCopyOfDummy();
    DummyObject object = dummyAndDeepCopyOfDummy.first;
    DummyObject deepCopy = dummyAndDeepCopyOfDummy.second;

    assertThat(object == deepCopy, is(false));
  }

  @Test
  public void dummyAndDummyCopyContentsAreNotSameObjects() {
    Pair<DummyObject, DummyObject> dummyAndDeepCopyOfDummy = getDummyAndDeepCopyOfDummy();
    DummyObject object = dummyAndDeepCopyOfDummy.first;
    DummyObject deepCopy = dummyAndDeepCopyOfDummy.second;

    assertThat(deepCopy.getOtherObject(), notNullValue());
    assertThat(deepCopy.getOtherObject().getOtherObject(), notNullValue());
    assertThat(object.getOtherObject() == deepCopy.getOtherObject(), is(false));
    assertThat(object.getOtherObject() == deepCopy.getOtherObject(), is(false));
    assertThat(object.getOtherObject().getOtherObject() == deepCopy.getOtherObject().getOtherObject(), is(false));
  }

  @Test
  public void dummyAndDummyCopyHaveSameContent() {
    Pair<DummyObject, DummyObject> dummyAndCopy = getDummyAndDeepCopyOfDummy();
    DummyObject object = dummyAndCopy.first;
    DummyObject deepCopy = dummyAndCopy.second;

    assertThat(object.getInteger() == deepCopy.getInteger(), is(true));
    assertThat(object.getOtherObject().getInteger() == deepCopy.getOtherObject().getInteger(), is(true));
    assertThat(Objects.equals(object.getString(), deepCopy.getString()), is(true));
    assertThat(Objects.equals(object.getOtherObject().getString(), deepCopy.getOtherObject().getString()), is(true));
  }

  private DummyObject createDummyObject() {
    return DummyObject.builder()
        .integer(1)
        .string("something").otherObject(
            DummyObject.builder()
                .integer(2)
                .string("something else")
                .otherObject(DummyObject.builder().build()).build()
        )
        .build();
  }

  private Pair<DummyObject, DummyObject> getDummyAndDeepCopyOfDummy() {
    DummyObject object = createDummyObject();
    ObjectHelper objectHelper = new ObjectHelper(new Gson());

    DummyObject deepCopy = objectHelper.deepCopy(object, DummyObject.class);
    return new Pair<>(object, deepCopy);
  }

  @Test
  public void xIsGreaterThanYByOne() {
    assertThat(ObjectHelper.compare(2, 1), is(1));
  }

  @Test
  public void xIsGreaterThanYByALot() {
    assertThat(ObjectHelper.compare(500, 1), is(1));
  }

  @Test
  public void xIsLessThanYByOne() {
    assertThat(ObjectHelper.compare(1, 2), is(-1));
  }

  @Test
  public void xIsLessThanYByALot() {
    assertThat(ObjectHelper.compare(1, 500), is(-1));
  }

  @Test
  public void xIsEqualY() {
    assertThat(ObjectHelper.compare(1, 1), is(0));
  }

  @Data
  @Builder
  private static final class DummyObject {
    String string;
    int integer;
    DummyObject otherObject;
  }
}
