package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.helpers.TestingTree;
import com.xmartlabs.base.core.rx.error.MaybeObserverWithErrorHandling;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MaybeMainObserversWithErrorHandlingTest {
  private TestingTree testingTree = new TestingTree();

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    Timber.plant(testingTree);
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling<>(MaybeObserver, ObservableResult.ERROR_HANDLING_ACTION));
  }

  @Test
  public void callsMaybeSubscribe() {
    Maybe.just(new Object())
        .doOnSubscribe(disposable ->
            Timber.tag(ObservableResult.SUBSCRIBE.toString()).i(ObservableResult.SUBSCRIBE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUBSCRIBE.toString()).getTag(),
        equalTo(ObservableResult.SUBSCRIBE.toString()));
  }

  @Test
  public void callsMaybeOnSuccessWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnSuccess(o -> Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(ObservableResult.SUCCESS.toString()));
  }

  @Test
  public void doesNotCallMaybeOnSuccessWhenNoObjectIsEmmited() {
    Maybe.empty()
        .doOnSuccess(o -> Timber.tag(ObservableResult.SUCCESS.toString()).i(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallMaybeOnCompleteWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnComplete(() ->
            Timber.tag(ObservableResult.ON_COMPLETE.toString()).i(ObservableResult.ON_COMPLETE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void callsMaybeOnCompleteWhenNoObjectOmited() {
    Maybe.empty()
        .doOnComplete(() ->
            Timber.tag(ObservableResult.ON_COMPLETE.toString()).i(ObservableResult.ON_COMPLETE.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ON_COMPLETE.toString()).getTag(),
        equalTo(ObservableResult.ON_COMPLETE.toString()));
  }

  @Test
  public void callsMaybeOnError() {
    Maybe.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void doesNotCallOnSuccessWhenError() {
    Maybe.error(Throwable::new)
        .doOnSuccess(o -> Timber.e(ObservableResult.SUCCESS.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.SUCCESS.toString()).getTag(),
        equalTo(TestingTree.DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Maybe.error(Throwable::new)
        .doOnError(throwable ->
            Timber.tag(ObservableResult.DO_ON_ERROR.toString()).i(ObservableResult.DO_ON_ERROR.toString()))
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.DO_ON_ERROR.toString()).getTag(),
        equalTo(ObservableResult.DO_ON_ERROR.toString()));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Maybe.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(testingTree.getLogTreeNodeWithTag(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()).getTag(),
        equalTo(ObservableResult.ENTERED_HOOK_ERROR_HANDLE.toString()));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Maybe.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(
        testingTree.getLogTreeExceptionDetailMessage(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString()),
        equalTo(ObservableResult.EXCEPTION_WHILE_ON_ERROR_HOOK_HANDLE.toString())
    );
  }

  @Test
  public void callsHookOnErrorAndMaybeOnError() {
    Maybe.error(Throwable::new)
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
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsMaybeOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, ObservableResult.ERROR_HANDLING_ACTION_WITH_ERROR_INSIDE));

    Maybe.error(Throwable::new)
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
