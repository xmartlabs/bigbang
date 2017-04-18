package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.SingleObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SingleMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnSingleSubscribe((single, singleObserver) ->
        new SingleObserverWithErrorHandling<>(singleObserver, ObservableResult.ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsSingleSubscribe() {
    Single.just(new Object())
        .doOnSubscribe(disposable ->
            Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE.toString()).getTag(),
        equalTo(ObservableResult.SUBSCRIBE.toString()));
  }

  @Test
  public void callsSingleOnSuccess() {
    Single.just(new Object())
        .doOnSuccess(o -> Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(ObservableResult.SUCCESS.toString()));
  }

  @Test
  public void callsSingleOnError() {
    Single.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnSuccessWhenError() {
    Single.error(Throwable::new)
        .doOnSuccess(o -> Timber.e(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Single.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }

  @Test
  public void callsHookOnErrorAndSingleOnError() {
    Single.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsSingleOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnSingleSubscribe((Single, SingleObserver) ->
        new SingleObserverWithErrorHandling(SingleObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Single.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }
}
