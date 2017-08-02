package com.xmartlabs.bigbang.retrofit.helper

import com.xmartlabs.bigbang.core.helper.GeneralErrorHelper
import com.xmartlabs.bigbang.core.helper.ServiceHelper
import com.xmartlabs.bigbang.core.log.LoggerTree
import com.xmartlabs.bigbang.retrofit.exception.ServiceExceptionWithMessage
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

/**
 * Handles [HttpException] and [ServiceExceptionWithMessage] exceptions emitted by the
 * [GeneralErrorHelper].
 */
class ServiceErrorHandler @Inject constructor() {
  companion object {
    private val LOGGER_KEY_RESPONSE_BODY = "response_body"
    private val LOGGER_KEY_RESPONSE_HEADERS = "response_headers"
    private val LOGGER_KEY_STATUS_CODE = "status_code"
    private val LOGGER_KEY_URL = "url"
  }
  
  @Inject
  internal lateinit var generalErrorHelper: GeneralErrorHelper
  @Inject
  internal lateinit var loggerTree: LoggerTree
  
  private val serviceErrorHandler = { serviceException: Throwable ->
    val exception = serviceException as ServiceExceptionWithMessage
    
    val information = HashMap<String, String>()
    information.put(LOGGER_KEY_URL, ServiceHelper.getUrl(exception.response.raw()))
    information.put(LOGGER_KEY_STATUS_CODE, exception.code.toString())
    information.put(LOGGER_KEY_RESPONSE_HEADERS, exception.response.headers().toString())
    information.put(LOGGER_KEY_RESPONSE_BODY, exception.errorBody ?: "")
    
    loggerTree.log(information, exception)
  }
  
  /**
   * Handles the [HttpException] and [ServiceExceptionWithMessage] exceptions, logging them if any error
   * is present.
   */
  fun handleServiceErrors() {
    generalErrorHelper.setErrorHandlerForThrowable(ServiceExceptionWithMessage::class) { ex -> serviceErrorHandler(ex) }
    generalErrorHelper.setErrorHandlerForThrowable(HttpException::class) { throwable ->
      when (throwable) {
        is HttpException -> serviceErrorHandler(ServiceExceptionWithMessage(throwable))
      }
    }
  }
}
