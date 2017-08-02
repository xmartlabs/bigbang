package com.xmartlabs.bigbang.core.observers

import timber.log.Timber

enum class ObservableResult {
  DO_ON_ERROR,
  ENTERED_HOOK_ERROR_HANDLE,
  EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE,
  ON_COMPLETE,
  ON_NEXT,
  SUCCESS,
  SUBSCRIBE,
  ;

  companion object {
    val ERROR_HANDLING_ACTION: (Throwable) -> Unit =
        { Timber.tag(ENTERED_HOOK_ERROR_HANDLE.toString()).i(ENTERED_HOOK_ERROR_HANDLE.toString()) }
    val ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE: (Throwable) -> Unit =
        { Timber.log(1, Exception(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())) }
  }
}
