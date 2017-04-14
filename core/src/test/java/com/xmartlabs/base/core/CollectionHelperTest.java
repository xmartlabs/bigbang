package com.xmartlabs.base.core;

import com.xmartlabs.base.core.helper.CollectionHelper;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionHelperTest {
  @Test
  public void testIsNullOrEmptyTrue() {
    Collection collection = new ArrayList();

    Assert.assertTrue(CollectionHelper.isNullOrEmpty(null));
    Assert.assertTrue(CollectionHelper.isNullOrEmpty(new ArrayList()));
    Assert.assertTrue(CollectionHelper.isNullOrEmpty(collection));
  }

  @Test
  public void testIsNullOrEmptyFalse() {
    ArrayList<Object> list = new ArrayList<>();
    list.add(new Object());

    Assert.assertFalse(CollectionHelper.isNullOrEmpty(list));
    //noinspection RedundantCast
    Assert.assertFalse(CollectionHelper.isNullOrEmpty((Collection<Object>) list));
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
    Assert.assertEquals(expectedList, list);
  }
}
