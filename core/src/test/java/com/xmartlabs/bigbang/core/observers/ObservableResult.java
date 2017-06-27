package com.xmartlabs.bigbang.core.observers;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

public enum ObservableResult {
  DO_ON_ERROR,
  ENTERED_HOOK_ERROR_HANDLE,
  EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE,
  ON_COMPLETE,
  ON_NEXT,
  SUCCESS,
  SUBSCRIBE,
  ;

  public static final Consumer<? super Throwable> ERROR_HANDLING_ACTION = t ->
      Timber.tag(ENTERED_HOOK_ERROR_HANDLE.toString()).i(ENTERED_HOOK_ERROR_HANDLE.toString());

  public static final Consumer<? super Throwable> ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE = t ->
      Timber.log(1, new Exception(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()));
}
