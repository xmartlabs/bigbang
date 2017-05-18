package com.xmartlabs.template.model;

import com.xmartlabs.bigbang.core.model.SessionType;

import lombok.Data;

@Data
public class Session implements SessionType {
  //TODO: Add fields that you wan to keep in the session
  String accessToken;
}
