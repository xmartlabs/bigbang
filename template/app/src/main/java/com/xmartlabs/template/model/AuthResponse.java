package com.xmartlabs.template.model;


import org.parceler.Parcel;

import lombok.Data;

@Data
@Parcel
public class AuthResponse {
  //TODO: check service auth response to match app's AuthResponse fields
  String accessToken;
  String scope;
  String tokenType;
}
