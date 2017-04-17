package com.xmartlabs.base.core;

import com.xmartlabs.base.core.helper.CollectionHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CollectionHelperTest {
  @Test
  public void testIsNullOrEmptyTrue() {
    Collection collection = new ArrayList();

    assertThat(CollectionHelper.isNullOrEmpty(null), is(true));
    assertThat(CollectionHelper.isNullOrEmpty(new ArrayList()), is(true));
    assertThat(CollectionHelper.isNullOrEmpty(collection), is(true));
  }

  @Test
  public void testIsNullOrEmptyFalse() {
    ArrayList<Object> list = new ArrayList<>();
    list.add(new Object());

    assertThat(CollectionHelper.isNullOrEmpty(list), is(false));
    //noinspection RedundantCast
    assertThat(CollectionHelper.isNullOrEmpty((Collection<Object>) list), is(false));
  }

  @Test
  public void testGetSortedDescComparator() {
    List<String> list = Arrays.asList(
        "First",
        "Awesome",
        "Second"
    );
    list.sort(CollectionHelper.getSortedDescComparator());

    List<String> expectedList = Arrays.asList(
        "Second",
        "First",
        "Awesome"
        );
    assertThat(expectedList, equalTo(list));
  }
}
