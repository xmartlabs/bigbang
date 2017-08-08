package com.xmartlabs.bigbang.core

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.security.GeneralSecurityException

class GeneralErrorHelperTest {
  private var calls: Int = 0

  @Throws(GeneralSecurityException::class)
  private fun generateException(): String {
    throw GeneralSecurityException("Something went wrong")
  }

  private fun initializeLogger() {
    calls = 0
    val generalErrorHelper = GeneralErrorHelper()
    generalErrorHelper.setErrorHandlerForThrowable(GeneralSecurityException::class) { calls++ }
    val config = TraceurConfig(
        true,
        Traceur.AssemblyLogLevel.NONE,
        generalErrorHelper.generalErrorAction)
    Traceur.enableLogging(config)
  }

  @Test
  fun multipleTimesSameException() {
    initializeLogger()
    Observable.fromCallable { this.generateException() }
        .map { it.trim { it <= ' ' } }
        .map { it.trim { it <= ' ' } }
        .subscribe()
    assertThat<Int>(calls, equalTo(1))
  }

  @Test
  fun multipleTimesDifferentExceptions() {
    initializeLogger()
    Observable.fromCallable { this.generateException() }
        .map { it.trim { it <= ' ' } }
        .map { it.trim { it <= ' ' } }
        .subscribe()
    Observable.fromCallable { this.generateException() }
        .map { it.trim { it <= ' ' } }
        .map { it.trim { it <= ' ' } }
        .subscribe()
    assertThat<Int>(calls, equalTo(2))
  }
}
