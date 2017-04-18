package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.FlowableObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FlowableMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnFlowableSubscribe((flowable, subscriber) ->
        new FlowableObserverWithErrorHandling<>(subscriber, ObservableResult.ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsFlowableSubscribe() {
    Flowable.just(new Object())
        .doOnSubscribe(disposable ->
            Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE.toString()).getTag(),
        equalTo(ObservableResult.SUBSCRIBE.toString()));
  }

  @Test
  public void callsFlowableOnComplete() {
    Flowable.just(new Object())
        .doOnComplete(() -> Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(ObservableResult.SUCCESS.toString()));
  }

  @Test
  public void callsFlowableOnNextCorrectAmountOfTimes() {
    Flowable.range(0, 5)
        .doOnNext(objects -> Timber.tag(ObservableResult.ON_NEXT.toString()).i(ObservableResult.ON_NEXT.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodesCountWithTag(ObservableResult.ON_NEXT.toString()), equalTo(5L));
  }

  @Test
  public void callsFlowableOnError() {
    Flowable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Flowable.error(Throwable::new)
        .doOnComplete(() -> Timber.e(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Flowable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Flowable.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Flowable.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }

  @Test
  public void callsHookOnErrorAndFlowableOnError() {
    Flowable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsFlowableOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Flowable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }
}
