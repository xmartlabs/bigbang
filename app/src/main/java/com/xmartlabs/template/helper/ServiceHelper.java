package com.xmartlabs.template.helper;

import android.support.annotation.NonNull;

import com.annimon.stream.Objects;

import retrofit2.Response;

/**
 * Created by medina on 16/09/2016.
 */
public class ServiceHelper {
  public static boolean isRequestOf(Response<?> response, String lastPaths) {
    return isRequestOf(response.raw(), lastPaths);
  }

  public static boolean isRequestOf(okhttp3.Response response, String lastPaths) {
    String[] responseUrl = getUrl(response).split("/");

    if (lastPaths.contains("/") && responseUrl.length > 1) {
      String[] lastPathsArray = lastPaths.split("/");
      return Objects.equals(responseUrl[responseUrl.length - 2], lastPathsArray[0]);
    } else {
      String actualLastPath = responseUrl[responseUrl.length - 1];
      if (actualLastPath.contains("?")) {
        actualLastPath = actualLastPath.substring(0, actualLastPath.indexOf("?"));
      }

      return Objects.equals(actualLastPath, lastPaths);
    }
  }

  public static String getUrl(okhttp3.Response response) {
    return response.request().url().toString();
  }

  public static String addParameterToUrl(@NonNull String url, @NonNull String parameter, @NonNull String value) {
    return url.replaceAll(
        String.format("(.*)\\{%s\\}(.*)", parameter),
        String.format("$1%s$2", value)
    );
  }

  public static String addParameterNameToEndOfUrl(@NonNull String url, @NonNull String parameterId) {
    return url + "/{" + parameterId + "}";
  }
}
