package com.xmartlabs.bigbang.core.providers

import com.xmartlabs.bigbang.core.common.EntityProvider
import com.xmartlabs.bigbang.core.repository.CoreSessionRepository
import com.xmartlabs.bigbang.core.module.SessionInterceptor
import javax.inject.Inject

/**
 * Provides the access token in order to be added in the service requests.
 * It's used by the [SessionInterceptor].
 */
open class AccessTokenProvider @Inject constructor(val sessionRepository: CoreSessionRepository)
  : EntityProvider<String> {
  companion object {
    protected val AUTH_TOKEN_HEADER_KEY = "session"
  }

  override fun provideEntity() = sessionRepository.abstractSession?.accessToken

  /**
   * Provides the access token header key.
   * This should be overridden to change the header key.
   *
   * @return the access token header key
   */
  open fun provideAccessTokenHeaderKey() = AUTH_TOKEN_HEADER_KEY

  override fun updateEntity(entity: String) {
    sessionRepository.abstractSession
        ?.apply { accessToken = entity }
        ?.let(sessionRepository::saveSession)
  }

  override fun deleteEntity() {
    sessionRepository.abstractSession
        ?.apply { accessToken = null }
        ?.let(sessionRepository::saveSession)
  }
}
