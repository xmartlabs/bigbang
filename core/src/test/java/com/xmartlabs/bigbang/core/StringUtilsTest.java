package com.xmartlabs.bigbang.core;

import com.xmartlabs.bigbang.core.helper.StringUtils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringUtilsTest {
  @Test
  public void nullString() {
    assertThat(StringUtils.isNullOrEmpty(null), is(true));
  }

  @Test
  public void emptyString() {
    assertThat(StringUtils.isNullOrEmpty(""), is(true));
  }

  @Test
  public void length3String() {
    assertThat(StringUtils.isNullOrEmpty("abc"), is(false));
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
