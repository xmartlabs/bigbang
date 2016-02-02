package com.xmartlabs.template.helper;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by santiago on 02/02/16.
 */
public class StringUtilsTest {
  @Test
  public void nullString() {
    Assert.assertTrue(StringUtils.stringIsNullOrEmpty(null));
  }

  @Test
  public void emptyString() {
    Assert.assertTrue(StringUtils.stringIsNullOrEmpty(""));
  }

  @Test
  public void length3String() {
    Assert.assertFalse(StringUtils.stringIsNullOrEmpty("abc"));
  }
}
