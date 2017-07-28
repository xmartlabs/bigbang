package com.xmartlabs.bigbang.core.observers

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helpers.TestingTree
import io.reactivex.Maybe
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class MaybeMainObserversWithErrorHandlingTest {
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
  fun callsMaybeSubscribe() {
    Maybe.just(Any())
        .doOnSubscribe { TestingTree.log(ObservableResult.SUBSCRIBE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).tag,
        equalTo(ObservableResult.SUBSCRIBE.toString()))
  }

  @Test
  fun callsMaybeOnSuccessWhenOneObjectIsEmmited() {
    Maybe.just(Any())
        .doOnSuccess { TestingTree.log(ObservableResult.SUCCESS) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(ObservableResult.SUCCESS.toString()))
  }

  @Test
  fun doesNotCallMaybeOnSuccessWhenNoObjectIsEmmited() {
    Maybe.empty<Any>()
        .doOnSuccess { TestingTree.log(ObservableResult.SUCCESS) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallMaybeOnCompleteWhenOneObjectIsEmmited() {
    Maybe.just(Any())
        .doOnComplete { TestingTree.log(ObservableResult.ON_COMPLETE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun callsMaybeOnCompleteWhenNoObjectOmited() {
    Maybe.empty<Any>()
        .doOnComplete { TestingTree.log(ObservableResult.ON_COMPLETE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).tag,
        equalTo(ObservableResult.ON_COMPLETE.toString()))
  }

  @Test
  fun callsMaybeOnError() {
    Maybe.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun doesNotCallOnSuccessWhenError() {
    Maybe.error<Any>(Throwable())
        .doOnSuccess { Timber.e(ObservableResult.SUCCESS.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Maybe.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun callsHookErrorHandleWhenNoDoOnError() {
    Maybe.error<Any>(Throwable())
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Maybe.error<Any>(Throwable())
        .subscribe { }

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }

  @Test
  fun callsHookOnErrorAndMaybeOnError() {
    Maybe.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnSuccess { assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsItAndCallsMaybeOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Maybe.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnSuccess { assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }
}
