package com.xmartlabs.bigbang.core.observers

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helpers.TestingTree
import io.reactivex.Observable
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class ObserversWithErrorHandlingTest {
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
  fun callsObservableSubscribe() {
    Observable.empty<Any>()
        .doOnSubscribe { Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).tag,
        equalTo(ObservableResult.SUBSCRIBE.toString()))
  }

  @Test
  fun callsObservableOnComplete() {
    Observable.empty<Any>()
        .doOnComplete { Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(ObservableResult.SUCCESS.toString()))
  }

  @Test
  fun callsObservableOnNextCorrectAmountOfTimes() {
    Observable.range(0, 5)
        .doOnNext { Timber.tag(ObservableResult.ON_NEXT.toString()).i(ObservableResult.ON_NEXT.toString()) }
        .subscribe { }

    assertThat(testingTree.getLogTreeNodesCountWithTag(ObservableResult.ON_NEXT), equalTo(5))
  }

  @Test
  fun callsObservableOnError() {
    Observable.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun doesNotCallOnCompleteWhenError() {
    Observable.error<Any>(Throwable())
        .doOnComplete { Timber.e(ObservableResult.SUCCESS.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)

    Observable.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun callsHookErrorHandleWhenNoDoOnError() {
    Observable.error<Any>(Throwable())
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Observable.error<Any>(Throwable())
        .subscribe { }

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()))
  }

  @Test
  fun callsHookOnErrorAndObservableOnError() {
    Observable.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnComplete { assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsItAndCallsObservableOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Observable.error<Any>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnComplete { assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }
}
