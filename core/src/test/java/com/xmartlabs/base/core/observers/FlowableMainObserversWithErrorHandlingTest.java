package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.rx.error.FlowableObserverWithErrorHandling;

import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FlowableMainObserversWithErrorHandlingTest extends MainObserversWithErrorHandlingTest {
  @Override
  @SuppressWarnings("unchecked")
  protected void setObserverSubscriber() {
    RxJavaPlugins.setOnFlowableSubscribe((flowable, subscriber) ->
        new FlowableObserverWithErrorHandling<>(subscriber, getErrorHandlingAction()));
  }

  @Test
  public void callsFlowableSubscribe() {
    Flowable.fromCallable(Object::new)
        .doOnSubscribe(disposable -> Timber.tag(OBSERVABLE_SUBSCRIBE).i(OBSERVABLE_SUBSCRIBE))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUBSCRIBE).getTag(), equalTo(OBSERVABLE_SUBSCRIBE));
  }

  @Test
  public void callsFlowableOnComplete() {
    Flowable.fromCallable(Object::new)
        .doOnComplete(() -> Timber.tag(OBSERVABLE_SUCCESS).i(OBSERVABLE_SUCCESS))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(OBSERVABLE_SUCCESS));
  }

  @Test
  public void callsFlowableOnNextCorrectAmountOfTimes() {
    Flowable.range(0, 5)
        .doOnNext(objects -> Timber.tag(OBSERVABLE_ON_NEXT).i(OBSERVABLE_ON_NEXT))
        .subscribe(o -> {});

    assertThat(getLogTreeNodesCountWithTag(OBSERVABLE_ON_NEXT), equalTo(5L));
  }

  @Test
  public void callsFlowableOnError() {
    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .doOnComplete(() -> Timber.e(OBSERVABLE_SUCCESS))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .subscribe(o -> {});

    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK),
        equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }

  @Test
  public void callsHookOnErrorAndFlowableOnError() {
    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsFlowableOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnFlowableSubscribe((observable, observer) ->
        new FlowableObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Flowable
        .fromCallable(() -> {
          throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
        })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK),
        equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }
}
