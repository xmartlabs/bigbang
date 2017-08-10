package com.xmartlabs.bigbang.core.helper

import com.xmartlabs.bigbang.core.extensions.handleException
import okhttp3.Response
import okio.Buffer
import okio.BufferedSource
import timber.log.Timber
import java.nio.charset.Charset

object ServiceHelper {
  /**
   * Retrieves the URL of the request.
   *
   * @param response the response from which the URL will be extracted
   * *
   * @return the requested URL
   */
  fun getUrl(response: Response) = response.request().url().toString()

  /**
   * Replaces all instances of the `parameter`, in the form of {parameter} with the given `value`.
   * For instance:
   * Given `url`: http://google.com/{query}, `parameter`: query and `value`: test
   * Returns: http://google.com/test
   *
   * @param url the url to replace the parameter
   * *
   * @param parameter the parameter to be replaced
   * *
   * @param value the value to replace the parameter
   * *
   * @return the `url` with all the instances of {`parameter`} replaced with `value`
   */
  fun addParameterToUrl(url: String, parameter: String, value: String) =
      url.replace(String.format("(.*)\\{%s\\}(.*)", parameter).toRegex(), String.format("$1%s$2", value))

  /**
   * Adds a parameter to the end of the URL, with the following form:
   * Given `url`: http://google.com and `parameterId`: query
   * Returns: http://google.com/{query}
   *
   * @param url The URL to add the parameter to
   * *
   * @param parameterId the name of the parameter
   * *
   * @return the `url` with the appended parameter
   */
  fun addParameterNameToEndOfUrl(url: String, parameterId: String) = "$url/{$parameterId}"

  /**
   * Returns the string representation of the service response.
   * The buffer is cloned in order to be able to be consumed again.
   *
   * @param response The service response
   * *
   * @return the `String` representation of the service response
   */
  fun cloneResponseAndGetAsString(response: BufferedSource) =
      handleException(Timber::w) {
        val buffer = response as Buffer
        response.request(java.lang.Long.MAX_VALUE)
        buffer.clone().readString(Charset.forName("UTF-8"))
      }
}
