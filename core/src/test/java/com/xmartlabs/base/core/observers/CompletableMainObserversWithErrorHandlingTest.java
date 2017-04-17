package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.CompletableObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static com.xmartlabs.base.core.helpers.TestingTree.DEFAULT_NOT_FOUND_TREE_NODE;
import static com.xmartlabs.base.core.observers.ObservableResult.DO_ON_ERROR;
import static com.xmartlabs.base.core.observers.ObservableResult.ENTERED_HOOK_ERROR_HANDLE;
import static com.xmartlabs.base.core.observers.ObservableResult.ERROR_HANDLING_ACTION;
import static com.xmartlabs.base.core.observers.ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE;
import static com.xmartlabs.base.core.observers.ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE;
import static com.xmartlabs.base.core.observers.ObservableResult.ON_COMPLETE;
import static com.xmartlabs.base.core.observers.ObservableResult.SUBSCRIBE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CompletableMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsCompletableSubscribe() {
    Completable.fromCallable(() -> null)
        .doOnSubscribe(disposable -> Timber.tag(SUBSCRIBE.toString()).i(SUBSCRIBE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(SUBSCRIBE.toString()).getTag(), equalTo(SUBSCRIBE.toString()));
  }

  @Test
  public void callsCompletableOnComplete() {
    Completable.fromCallable(() -> null)
        .doOnComplete(() -> Timber.tag(ON_COMPLETE.toString()).i(ON_COMPLETE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ON_COMPLETE.toString()).getTag(), equalTo(ON_COMPLETE.toString()));
  }

  @Test
  public void callsCompletableOnError() {
    Completable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Completable.error(Throwable::new)
        .doOnComplete(() -> Timber.e(ON_COMPLETE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ON_COMPLETE.toString()).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Completable.error(Throwable::new)
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()));
  }

  @Test
  public void callsHookOnErrorAndCompletableOnError() {
    Completable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ENTERED_HOOK_ERROR_HANDLE.toString()));
    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsCompletableOnError() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(DO_ON_ERROR.toString()).i(DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(DO_ON_ERROR.toString()).getTag(), equalTo(DO_ON_ERROR.toString()));
    assertThat(testingTree.getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()));
  }
}
