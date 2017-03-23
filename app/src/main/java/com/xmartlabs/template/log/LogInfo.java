package com.xmartlabs.template.log;

import lombok.Builder;

@Builder
public class LogInfo {
  private int priority;
  private String tag;
  private String message;
}
