package com.xmartlabs.base.core.observers;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

public enum ObservableResult {
  SUCCESS,
  ON_COMPLETE,
  DO_ON_ERROR,
  ON_NEXT,
  SUBSCRIBE,
  ENTERED_HOOK_ERROR_HANDLE,
  EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE,
  ;

  public static final Consumer<? super Throwable> ERROR_HANDLING_ACTION = t ->
      Timber.tag(ENTERED_HOOK_ERROR_HANDLE.toString()).i(ENTERED_HOOK_ERROR_HANDLE.toString());

  public static final Consumer<? super Throwable> ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE = t -> {
    throw new Exception(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString());
  };
}
