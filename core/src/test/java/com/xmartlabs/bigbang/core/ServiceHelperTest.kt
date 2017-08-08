package com.xmartlabs.bigbang.core

import com.xmartlabs.bigbang.core.helper.ServiceHelper
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class ServiceHelperTest {
  companion object {
    private val REQUEST_URL = "https://github.com/xmartlabs/bigbang"
  }
  
  private var response: Response? = null

  @Before
  fun setUp() {
    val request = Request.Builder()
        .url(REQUEST_URL)
        .build()
    response = Response.Builder()
        .code(200).message("OK")
        .protocol(Protocol.HTTP_1_1)
        .request(request)
        .build()
  }

  @Test
  fun getUrl() = assertThat(ServiceHelper.getUrl(response!!), equalTo(REQUEST_URL))

  @Test
  fun addParameterNameToEndOfUrl() {
    var url = ServiceHelper.addParameterNameToEndOfUrl(REQUEST_URL, "test")
    url = ServiceHelper.addParameterNameToEndOfUrl(url, "test2")

    val expectedResult = REQUEST_URL + "/{test}/{test2}"

    assertThat(url, equalTo(expectedResult))
  }

  @Test
  fun addParameterToUrl() {
    var url = ServiceHelper.addParameterNameToEndOfUrl(REQUEST_URL, "test")
    url = ServiceHelper.addParameterNameToEndOfUrl(url, "test2")

    var parametrizedUrl = ServiceHelper.addParameterToUrl(url, "test", "replacedTest")
    parametrizedUrl = ServiceHelper.addParameterToUrl(parametrizedUrl, "test2", "replacedTest2")

    val expectedResult = REQUEST_URL + "/replacedTest/replacedTest2"
    assertThat(parametrizedUrl, equalTo(expectedResult))
  }
}
