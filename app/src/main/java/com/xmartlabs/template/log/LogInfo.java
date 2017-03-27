package com.xmartlabs.template.log;

import android.support.annotation.Nullable;

import lombok.Builder;
import lombok.Data;

/** Information associated with a captured exception or event-like exception. **/
@Builder
@Data
public class LogInfo {
  private int priority;
  @Nullable
  private String tag;
  @Nullable
  private String message;
}
