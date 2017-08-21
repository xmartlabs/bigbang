package com.xmartlabs.template.model

import org.parceler.Parcel

//TODO: check service auth response to match app's AuthResponse fields
@Parcel(Parcel.Serialization.BEAN)
class AuthResponse {
  var accessToken: String? = null
  var scope: String? = null
  var tokenType: String? = null
}
