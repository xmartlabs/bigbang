package com.xmartlabs.template.model

import com.xmartlabs.bigbang.core.model.SessionType

import lombok.Data

class Session: SessionType {
  var token: String? = null
  
  override fun setAccessToken(accessToken: String?) {
    token = accessToken
  }
  
  override fun getAccessToken() = token
}