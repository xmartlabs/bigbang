package com.xmartlabs.template.log;

import lombok.Builder;
import lombok.Data;

/** Information associated with a captured exception or event-like exception. **/
@Builder
@Data
public class LogInfo {
  private int priority;
  private String tag;
  private String message;
}
