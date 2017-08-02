package com.xmartlabs.bigbang.core.helper

import com.xmartlabs.bigbang.core.exception.EntityNotFoundException
import com.xmartlabs.bigbang.core.extensions.andMatches
import com.xmartlabs.bigbang.core.extensions.toArray
import io.reactivex.exceptions.CompositeException
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/** Handles any [Throwable] thrown.  */
@Singleton
class GeneralErrorHelper @Inject
constructor() {
  private val UNTRACKED_CLASSES: List<Class<out Exception>>
    get() = listOf(
        CancellationException::class.java,
        ConnectException::class.java,
        EntityNotFoundException::class.java,
        UnknownHostException::class.java
    )
  private val DUMMY_STACK_TRACE_ELEMENT = StackTraceElement("General error logger", "", null, -1)
  private val throwableHandlers = HashMap<KClass<out Throwable>, (Throwable) -> Unit>()

  val generalErrorAction: (Throwable) -> Unit = { t ->
    when(t) {
      is CompositeException -> t.exceptions.forEach(this::handleException)
      else -> handleException(t)
    }
  }

  private fun handleException(throwable: Throwable) {
    if (!shouldHandleThrowable(throwable) || throwable.cause.andMatches(this::shouldHandleThrowable)) {
      if (!exceptionIsAlreadyBeingHandled(throwable)) {
        Timber.d(throwable, "Untracked exception")
      }
    } else {
      if (throwableHandlers.containsKey(throwable::class)) {
        throwableHandlers[throwable::class]?.invoke(throwable)
      } else {
        Timber.e(throwable)
      }
    }
    markExceptionAsHandled(throwable)
  }

  private fun shouldHandleThrowable(throwable: Throwable) =
      !exceptionIsAlreadyBeingHandled(throwable) && !UNTRACKED_CLASSES.contains(throwable::class.java)

  private fun exceptionIsAlreadyBeingHandled(throwable: Throwable) =
      listOf(*throwable.stackTrace).any { it == DUMMY_STACK_TRACE_ELEMENT }

  private fun markExceptionAsHandled(throwable: Throwable) {
    throwable.stackTrace = mutableListOf(DUMMY_STACK_TRACE_ELEMENT)
        .apply { addAll(listOf(*throwable.stackTrace)) }
        .toArray()
  }

  /**
   * Add a new handler for a specific [Throwable] class.
   *
   * @param clazz   the [Throwable] class to be handled
   * *
   * @param handler the handler for the specified [Throwable]
   */
  fun setErrorHandlerForThrowable(clazz: KClass<out Throwable>, handler: (Throwable) -> Unit) =
      throwableHandlers.put(clazz, handler)
}
