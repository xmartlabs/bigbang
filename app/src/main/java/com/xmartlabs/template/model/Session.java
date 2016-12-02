package com.xmartlabs.template.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.parceler.Parcel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by santiago on 23/10/15.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Parcel
@ToString(callSuper = true)
public class Session extends AuthResponse {
  public static final int CURRENT_DATABASE_VERSION = 1;

  Integer databaseVersion;
  @Nullable
  String gcmToken;

  public void updateSession(@NonNull AuthResponse authResponse) {
  }
}
