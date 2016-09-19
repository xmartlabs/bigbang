package com.xmartlabs.template.helper;

import android.app.Service;

import org.junit.Before;
import org.junit.Test;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by medina on 19/09/2016.
 */
public class ServiceHelperTest {
  private static final String REQUEST_URL = "https://github.com/xmartlabs/Android-Base-Project";

  private Response response;

  @Before
  public void setUp() {
    Request request = new Request.Builder()
        .url(REQUEST_URL)
        .build();
    response = new Response.Builder()
        .code(200).message("OK")
        .protocol(Protocol.HTTP_1_1)
        .request(request)
        .build();
  }

  @Test
  public void getUrl() {
    assertThat(ServiceHelper.getUrl(response), equalTo(REQUEST_URL));
  }

  @Test
  public void addParameterNameToEndOfUrl() {
    String url = ServiceHelper.addParameterNameToEndOfUrl(REQUEST_URL, "test");
    url = ServiceHelper.addParameterNameToEndOfUrl(url, "test2");

    String expectedResult = REQUEST_URL + "/{test}/{test2}";

    assertThat(url, equalTo(expectedResult));
  }

  @Test
  public void addParameterToUrl() {
    String url = ServiceHelper.addParameterNameToEndOfUrl(REQUEST_URL, "test");
    url = ServiceHelper.addParameterNameToEndOfUrl(url, "test2");

    String parametrizedUrl = ServiceHelper.addParameterToUrl(url, "test", "replacedTest");
    parametrizedUrl = ServiceHelper.addParameterToUrl(parametrizedUrl, "test2", "replacedTest2");

    String expectedResult = REQUEST_URL + "/replacedTest/replacedTest2";
    assertThat(parametrizedUrl, equalTo(expectedResult));
  }
}
