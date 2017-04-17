package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.SingleObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static com.xmartlabs.base.core.helpers.TestingTree.DEFAULT_NOT_FOUND_TREE_NODE;
import static com.xmartlabs.base.core.observers.ObservableResult.DO_ON_ERROR;
import static com.xmartlabs.base.core.observers.ObservableResult.ENTERED_HOOK_ERROR_HANDLE;
import static com.xmartlabs.base.core.observers.ObservableResult.ERROR_HANDLING_ACTION;
import static com.xmartlabs.base.core.observers.ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE;
import static com.xmartlabs.base.core.observers.ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE;
import static com.xmartlabs.base.core.observers.ObservableResult.SUBSCRIBE;
import static com.xmartlabs.base.core.observers.ObservableResult.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SingleMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnSingleSubscribe((single, singleObserver) ->
        new SingleObserverWithErrorHandling<>(singleObserver, ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsSingleSubscribe() {
    Single.just(new Object())
        .doOnSubscribe(disposable -> Timber.tag(SUBSCRIBE.toString()).i(SUBSCRIBE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(SUBSCRIBE.toString()).getTag(), equalTo(SUBSCRIBE.toString()));
  }

  @Test
  public void callsSingleOnSuccess() {
    Single.just(new Object())
        .doOnSuccess(o -> Timber.tag(SUCCESS.toString()).i(SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(SUCCESS.toString()).getTag(), equalTo(SUCCESS.toString()));
  }

  @Test
  public void callsSingleOnError() {
    Single.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnSuccessWhenError() {
    Single.error(Throwable::new)
        .doOnSuccess(o -> Timber.e(SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(SUCCESS.toString()).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Single.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()));
  }

  @Test
  public void callsHookOnErrorAndSingleOnError() {
    Single.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ENTERED_HOOK_ERROR_HANDLE.toString()));
    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsSingleOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
    assertThat(testingTree.getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()));
  }
}
