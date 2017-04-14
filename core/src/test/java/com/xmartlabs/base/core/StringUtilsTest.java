package com.xmartlabs.base.core;

import com.xmartlabs.base.core.helper.StringUtils;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class StringUtilsTest {
  @Test
  public void nullString() {
    Assert.assertTrue(StringUtils.isNullOrEmpty(null));
  }

  @Test
  public void emptyString() {
    Assert.assertTrue(StringUtils.isNullOrEmpty(""));
  }

  @Test
  public void length3String() {
    Assert.assertFalse(StringUtils.isNullOrEmpty("abc"));
  }

  @Test
  public void capitalizeEmptyString() {
    assertThat(StringUtils.capitalizeWord(""), equalTo(""));
  }

  @Test
  public void capitalizeWord() {
    assertThat(StringUtils.capitalizeWord("word"), equalTo("Word"));
  }
}
