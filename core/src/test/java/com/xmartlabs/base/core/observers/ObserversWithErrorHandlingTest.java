package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.rx.error.ObserverWithErrorHandling;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ObserversWithErrorHandlingTest extends MainObserversWithErrorHandlingTest {
  @Override
  @SuppressWarnings("unchecked")
  protected void setObserverSubscriber() {
    RxJavaPlugins.setOnObservableSubscribe((observable, observer) ->
        new ObserverWithErrorHandling<>(observer, getErrorHandlingAction()));
  }

  @Test
  public void callsObservableSubscribe() {
    Observable.empty()
        .doOnSubscribe(disposable -> Timber.tag(OBSERVABLE_SUBSCRIBE).i(OBSERVABLE_SUBSCRIBE))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUBSCRIBE).getTag(), equalTo(OBSERVABLE_SUBSCRIBE));
  }

  @Test
  public void callsObservableOnComplete() {
    Observable.empty()
        .doOnComplete(() -> Timber.tag(OBSERVABLE_SUCCESS).i(OBSERVABLE_SUCCESS))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(OBSERVABLE_SUCCESS));
  }

  @Test
  public void callsObservableOnNextCorrectAmountOfTimes() {
    Observable.range(0, 5)
        .doOnNext(objects -> Timber.tag(OBSERVABLE_ON_NEXT).i(OBSERVABLE_ON_NEXT))
        .subscribe(o -> {});

    assertThat(getLogTreeNodesCountWithTag(OBSERVABLE_ON_NEXT), equalTo(5L));
  }

  @Test
  public void callsObservableOnError() {
    Observable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Observable.error(Throwable::new)
        .doOnComplete(() -> Timber.e(OBSERVABLE_SUCCESS))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnObservableSubscribe((observable, observer) ->
        new ObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Observable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Observable.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnObservableSubscribe((observable, observer) ->
        new ObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Observable.error(Throwable::new)
        .subscribe(o -> {});

    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }

  @Test
  public void callsHookOnErrorAndObservableOnError() {
    Observable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsObservableOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnObservableSubscribe((observable, observer) ->
        new ObserverWithErrorHandling<>(observer, getErrorHandlingActionWithErrorInside()));

    Observable.error(Throwable::new)
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(o -> {});

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }
}
