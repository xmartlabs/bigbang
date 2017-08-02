package com.xmartlabs.bigbang.core.observers

import com.tspoon.traceur.Traceur
import com.tspoon.traceur.TraceurConfig
import com.xmartlabs.bigbang.core.helpers.TestingTree
import io.reactivex.Flowable
import io.reactivex.annotations.NonNull
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import timber.log.Timber

class FlowableMainObserversWithErrorHandlingTest {
  private val testingTree = TestingTree()

  @Before
  fun setUp() {
    Timber.plant(testingTree)
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION)
  }

  private fun setUpLog(@NonNull action: (Throwable) -> Unit) {
    val config = TraceurConfig(
        true,
        Traceur.AssemblyLogLevel.NONE,
        action)
    Traceur.enableLogging(config)
  }

  @Test
  fun callsFlowableSubscribe() {
    Flowable.just(Any())
        .doOnSubscribe { TestingTree.log(ObservableResult.SUBSCRIBE) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).tag,
        equalTo(ObservableResult.SUBSCRIBE.toString()))
  }

  @Test
  fun callsFlowableOnComplete() {
    Flowable.just(Any())
        .doOnComplete { TestingTree.log(ObservableResult.SUCCESS) }
        .subscribe {  }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(ObservableResult.SUCCESS.toString()))
  }

  @Test
  fun callsFlowableOnNextCorrectAmountOfTimes() {
    Flowable.range(0, 5)
        .doOnNext { TestingTree.log(ObservableResult.ON_NEXT) }
        .subscribe { }

    assertThat(testingTree.getLogTreeNodesCountWithTag(ObservableResult.ON_NEXT), equalTo(5))
  }

  @Test
  fun callsFlowableOnError() {
    Flowable.error<Throwable>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun doesNotCallOnCompleteWhenError() {
    Flowable.error<Throwable>(Throwable())
        .doOnComplete { Timber.e(ObservableResult.SUCCESS.toString()) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).tag,
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE))
  }

  @Test
  fun doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Flowable.error<Throwable>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun callsHookErrorHandleWhenNoDoOnError() {
    Flowable.error<Throwable>(Throwable())
        .subscribe{ }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Flowable.error<Throwable>(Throwable())
        .subscribe { }

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    )
  }

  @Test
  fun callsHookOnErrorAndFlowableOnError() {
    Flowable.error<Throwable>(Throwable())
        .doOnError { TestingTree.log(ObservableResult.DO_ON_ERROR) }
        .doOnComplete { assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")) }
        .subscribe { }

    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).tag,
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()))
    assertThat<String>(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).tag,
        equalTo(ObservableResult.DO_ON_ERROR.toString()))
  }

  @Test
  fun catchesHookOnErrorCallbackExceptionAndLogsItAndCallsFlowableOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE)
  
    Flowable.error<Throwable>(Throwable())
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
