package com.xmartlabs.bigbang.core.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

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
   * Replaces all instances of the {@code parameter}, in the form of {parameter} with the given {@code value}.
   * For instance:
   * Given {@code url}: http://google.com/{query}, {@code parameter}: query and {@code value}: test
   * Returns: http://google.com/test
   *
   * @param url the url to replace the parameter
   * @param parameter the parameter to be replaced
   * @param value the value to replace the parameter
   * @return the {@code url} with all the instances of {{@code parameter}} replaced with {@code value}
   */
  public static String addParameterToUrl(@NonNull String url, @NonNull String parameter, @NonNull String value) {
    return url.replaceAll(
        String.format("(.*)\\{%s\\}(.*)", parameter),
        String.format("$1%s$2", value)
    );
  }

  /**
   * Adds a parameter to the end of the URL, with the following form:
   * Given {@code url}: http://google.com and {@code parameterId}: query
   * Returns: http://google.com/{query}
   *
   * @param url The URL to add the parameter to
   * @param parameterId the name of the parameter
   * @return the {@code url} with the appended parameter
   */
  public static String addParameterNameToEndOfUrl(@NonNull String url, @NonNull String parameterId) {
    return url + "/{" + parameterId + "}";
  }


  /**
   * Returns the string representation of the service response.
   * The buffer is cloned in order to be able to be consumed again.
   *
   * @param response The service response
   * @return the {@code String} representation of the service response
   */
  @Nullable
  public static String cloneResponseAndGetAsString(BufferedSource response) {
    try {
      Buffer buffer = (Buffer) response;
      response.request(Long.MAX_VALUE);
      return buffer.clone().readString(Charset.forName("UTF-8"));
    } catch (IOException e) {
      Timber.w(e);
    }
    return null;
  }
}
