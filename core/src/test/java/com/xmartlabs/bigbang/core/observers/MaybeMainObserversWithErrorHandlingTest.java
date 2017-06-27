package com.xmartlabs.bigbang.core.observers;

import com.tspoon.traceur.Traceur;
import com.tspoon.traceur.TraceurConfig;
import com.xmartlabs.bigbang.core.helpers.TestingTree;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MaybeMainObserversWithErrorHandlingTest {
  private final TestingTree testingTree = new TestingTree();

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    Timber.plant(testingTree);
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION);
  }

  private void setUpLog(@NonNull Consumer<? super Throwable> action) {
    TraceurConfig config = new TraceurConfig(
        true,
        Traceur.AssemblyLogLevel.NONE,
        action::accept);
    Traceur.enableLogging(config);
  }

  @Test
  public void callsMaybeSubscribe() {
    Maybe.just(new Object())
        .doOnSubscribe(disposable -> TestingTree.log(ObservableResult.SUBSCRIBE))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE).getTag(),
        equalTo(ObservableResult.SUBSCRIBE.toString()));
  }

  @Test
  public void callsMaybeOnSuccessWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnSuccess(o -> TestingTree.log(ObservableResult.SUCCESS))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).getTag(),
        equalTo(ObservableResult.SUCCESS.toString()));
  }

  @Test
  public void doesNotCallMaybeOnSuccessWhenNoObjectIsEmmited() {
    Maybe.empty()
        .doOnSuccess(o -> TestingTree.log(ObservableResult.SUCCESS))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallMaybeOnCompleteWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnComplete(() -> TestingTree.log(ObservableResult.ON_COMPLETE))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void callsMaybeOnCompleteWhenNoObjectOmited() {
    Maybe.empty()
        .doOnComplete(() -> TestingTree.log(ObservableResult.ON_COMPLETE))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE).getTag(),
        equalTo(ObservableResult.ON_COMPLETE.toString()));
  }

  @Test
  public void callsMaybeOnError() {
    Maybe.error(Throwable::new)
        .doOnError(throwable -> TestingTree.log(ObservableResult.DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnSuccessWhenError() {
    Maybe.error(Throwable::new)
        .doOnSuccess(o -> Timber.e(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE);

    Maybe.error(Throwable::new)
        .doOnError(throwable -> TestingTree.log(ObservableResult.DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Maybe.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE);

    Maybe.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }

  @Test
  public void callsHookOnErrorAndMaybeOnError() {
    Maybe.error(Throwable::new)
        .doOnError(throwable -> TestingTree.log(ObservableResult.DO_ON_ERROR))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsMaybeOnError() {
    setUpLog(ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE);

    Maybe.error(Throwable::new)
        .doOnError(throwable -> TestingTree.log(ObservableResult.DO_ON_ERROR))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));

    assertThat(testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }
}
