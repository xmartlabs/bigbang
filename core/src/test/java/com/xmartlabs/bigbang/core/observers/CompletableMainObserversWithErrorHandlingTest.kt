package com.xmartlabs.bigbang.core.observers

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helpers.TestingTree
import io.reactivex.Completable
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class CompletableMainObserversWithErrorHandlingTest {
  private val testingTree = TestingTree()

  @Before
  fun setUp() {
    Timber.plant(testingTree)
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION)
  }

  private fun setUpLog(action: (Throwable) -> Unit) {
    val config = TraceurConfig(
        true,
        Traceur.AssemblyLogLevel.NONE,
        action)
    Traceur.enableLogging(config)
  }

  @Test
  fun callsCompletableSubscribe() {
    Completable.fromCallable { null }
        .doOnSubscribe { TestingTree.log(ObservableResult.SUBSCRIBE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).tag,
        equalTo(ObservableResult.SUBSCRIBE.toString()))
  }

  @Test
  fun callsCompletableOnComplete() {
    Completable.fromCallable { null }
        .doOnComplete { TestingTree.log(ObservableResult.ON_COMPLETE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).tag,
        equalTo(ObservableResult.ON_COMPLETE.toString()))
  }

  @Test
  fun callsCompletableOnError() {
    Completable.error(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun doesNotCallOnCompleteWhenError() {
    Completable.error(Throwable())
        .doOnComplete { Timber.e(ObservableResult.ON_COMPLETE.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)

    Completable.error(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun callsHookErrorHandleWhenNoDoOnError() {
    Completable.error(Throwable())
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)

    Completable.error(Throwable())
        .subscribe { }

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }

  @Test
  fun callsHookOnErrorAndCompletableOnError() {
    Completable.error(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnComplete { assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsItAndCallsCompletableOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)

    Completable.error(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnComplete { assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }
}
