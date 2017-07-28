package com.xmartlabs.bigbang.core.observers

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helpers.TestingTree
import io.reactivex.Single
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class SingleMainObserversWithErrorHandlingTest {
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
  fun callsSingleSubscribe() {
    Single.just(Any())
        .doOnSubscribe { Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).tag,
        equalTo(ObservableResult.SUBSCRIBE.toString()))
  }

  @Test
  fun callsSingleOnSuccess() {
    Single.just(Any())
        .doOnSuccess { Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(ObservableResult.SUCCESS.toString()))
  }

  @Test
  fun callsSingleOnError() {
    Single.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun doesNotCallOnSuccessWhenError() {
    Single.error<Any>(Throwable())
        .doOnSuccess { Timber.e(ObservableResult.SUCCESS.toString()) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
    Single.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun callsHookErrorHandleWhenNoDoOnError() {
    Single.error<Any>(Throwable())
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Single.error<Any>(Throwable())
        .subscribe { _ -> }

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }

  @Test
  fun callsHookOnErrorAndSingleOnError() {
    Single.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnSuccess { assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsItAndCallsSingleOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)

    Single.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnSuccess { assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")) }
        .subscribe { _ -> }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }
}
