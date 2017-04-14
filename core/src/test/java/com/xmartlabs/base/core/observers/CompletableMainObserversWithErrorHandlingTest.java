package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.rx.error.CompletableObserverWithErrorHandling;

import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CompletableMainObserversWithErrorHandlingTest extends MainObserversWithErrorHandlingTest {
  @Override
  protected void setObserverSubscriber() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, getErrorHandlingAction()));
  }

  @Test
  public void callsCompletableSubscribe() {
    Completable.fromCallable(() -> null)
        .doOnSubscribe(disposable -> Timber.tag(OBSERVABLE_SUBSCRIBE).i(OBSERVABLE_SUBSCRIBE))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUBSCRIBE).getTag(), equalTo(OBSERVABLE_SUBSCRIBE));
  }

  @Test
  public void callsCompletableOnComplete() {
    Completable.fromCallable(() -> null)
        .doOnComplete(() -> Timber.tag(OBSERVABLE_ON_COMPLETE).i(OBSERVABLE_ON_COMPLETE))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_ON_COMPLETE).getTag(), equalTo(OBSERVABLE_ON_COMPLETE));
  }

  @Test
  public void callsCompletableOnError() {
    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void doesNotCallOnCompleteWhenError() {
    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnComplete(() -> Timber.e(OBSERVABLE_ON_COMPLETE))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_ON_COMPLETE).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnCompleteWhenErrorAndErrorInHookErrorHandle() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, getErrorHandlingActionWithErrorInside()));

    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, getErrorHandlingActionWithErrorInside()));

    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .subscribe(() -> {
        });

    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }

  @Test
  public void callsHookOnErrorAndCompletableOnError() {
    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsCompletableOnError() {
    RxJavaPlugins.setOnCompletableSubscribe((completable, completableObserver) ->
        new CompletableObserverWithErrorHandling(completableObserver, getErrorHandlingActionWithErrorInside()));

    Completable.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnComplete(() -> assertThat("Executed OnComplete", equalTo("Didn't execute onComplete")))
        .subscribe(() -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }
}
