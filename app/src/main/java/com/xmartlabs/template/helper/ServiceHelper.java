package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;

/**
 * Created by medina on 19/09/2016.
 */
@SuppressWarnings("unused")
public class ServiceHelper {
  /**
   * Retrieves the URL of the request.
   *
   * @param response the response from which the URL will be extracted
   * @return the requested URL
   */
  public static String getUrl(okhttp3.Response response) {
    return response.request().url().toString();
  }

  /**
   * Replaces all instances of the <code>parameter</code>, in the form of {parameter} with the given <code>value</code>.
   * For instance:
   * Given <code>url</code>: http://google.com/{query}, <code>parameter</code>: query and <code>value</code>: test
   * Returns: http://google.com/test
   *
   * @param url the url to replace the parameter
   * @param parameter the parameter to be replaced
   * @param value the value to replace the parameter
   * @return the <code>url</code> with all the instances of {<code>parameter</code>} replaced with <code>value</code>
   */
  public static String addParameterToUrl(@NonNull String url, @NonNull String parameter, @NonNull String value) {
    return url.replaceAll(
        String.format("(.*)\\{%s\\}(.*)", parameter),
        String.format("$1%s$2", value)
    );
  }

  /**
   * Adds a parameter to the end of the URL, with the following form:
   * Given <code>url</code>: http://google.com and <code>parameterId</code>: query
   * Returns: http://google.com/{query}
   *
   * @param url The URL to add the parameter to
   * @param parameterId the name of the parameter
   * @return the <code>url</code> with the appended parameter
   */
  public static String addParameterNameToEndOfUrl(@NonNull String url, @NonNull String parameterId) {
    return url + "/{" + parameterId + "}";
  }
}
