package com.xmartlabs.template.model;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Parcel
@ToString(callSuper = true)
public class Session extends AuthResponse {
  String accessToken;
  Integer databaseVersion;

  public void updateSession(@NonNull AuthResponse authResponse) {
  }
}
