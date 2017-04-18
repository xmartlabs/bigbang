package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.CompletableObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CompletableMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, ObservableResult.ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsCompletableSubscribe() {
    Completable.fromCallable(() -> null)
        .doOnSubscribe(disposable ->
            Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE.toString()).getTag(),
        equalTo(ObservableResult.SUBSCRIBE.toString()));
  }

  @Test
  public void callsCompletableOnComplete() {
    Completable.fromCallable(() -> null)
        .doOnComplete(() ->
            Timber.tag(ObservableResult.ON_COMPLETE.toString()).i(ObservableResult.ON_COMPLETE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE.toString()).getTag(),
        equalTo(ObservableResult.ON_COMPLETE.toString()));
  }

  @Test
  public void callsCompletableOnError() {
    Completable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Completable.error(Throwable::new)
        .doOnComplete(() -> Timber.e(ObservableResult.ON_COMPLETE.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver,
            ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Completable.error(Throwable::new)
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver,
            ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeExceptionDetailMessage(
        ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }

  @Test
  public void callsHookOnErrorAndCompletableOnError() {
    Completable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsCompletableOnError() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver,
            ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Completable.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }
}
