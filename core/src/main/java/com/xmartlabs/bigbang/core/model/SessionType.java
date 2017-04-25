package com.xmartlabs.bigbang.core.model;

/** Marker interface that should be implemented by the Session object of the application **/
public interface SessionType {
  String getAccessToken();
  void setAccessToken(String accessToken);
}
