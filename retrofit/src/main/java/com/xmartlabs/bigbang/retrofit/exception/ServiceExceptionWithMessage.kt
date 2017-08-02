package com.xmartlabs.bigbang.retrofit.exception

import com.xmartlabs.bigbang.core.helper.ServiceHelper

import retrofit2.HttpException
import retrofit2.Response

class ServiceExceptionWithMessage constructor(exception: HttpException) : RuntimeException() {
  val code: Int
  override val message: String
  val errorBody: String?
  val response: Response<*> = exception.response()
  
  init {
    code = response.code()
    message = response.message()
    errorBody = ServiceHelper.cloneResponseAndGetAsString(response.errorBody().source())
    initCause(exception)
  }
}
