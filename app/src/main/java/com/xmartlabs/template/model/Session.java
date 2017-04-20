package com.xmartlabs.template.model;

import com.xmartlabs.base.core.model.SessionType;

import org.parceler.Parcel;

import lombok.Data;
import lombok.ToString;

@Data
@Parcel
@ToString(callSuper = true)
public class Session implements SessionType {
  String accessToken;
}
