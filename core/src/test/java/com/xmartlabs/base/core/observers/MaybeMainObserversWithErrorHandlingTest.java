package com.xmartlabs.base.core.observers;

import com.xmartlabs.base.core.rx.error.MaybeObserverWithErrorHandling;

import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MaybeMainObserversWithErrorHandlingTest extends MainObserversWithErrorHandlingTest {
  @Override
  @SuppressWarnings("unchecked")
  protected void setObserverSubscriber() {
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling<>(MaybeObserver, getErrorHandlingAction()));
  }

  @Test
  public void callsMaybeSubscribe() {
    Maybe.just(new Object())
        .doOnSubscribe(disposable -> Timber.tag(OBSERVABLE_SUBSCRIBE).i(OBSERVABLE_SUBSCRIBE))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUBSCRIBE).getTag(), equalTo(OBSERVABLE_SUBSCRIBE));
  }

  @Test
  public void callsMaybeOnSuccessWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnSuccess(o -> Timber.tag(OBSERVABLE_SUCCESS).i(OBSERVABLE_SUCCESS))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(OBSERVABLE_SUCCESS));
  }

  @Test
  public void doesNotCallMaybeOnSuccessWhenNoObjectIsEmmited() {
    Maybe.fromCallable(() -> null)
        .doOnSuccess(o -> Timber.tag(OBSERVABLE_SUCCESS).i(OBSERVABLE_SUCCESS))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallMaybeOnCompleteWhenOneObjectIsEmmited() {
    Maybe.just(new Object())
        .doOnComplete(() -> Timber.tag(OBSERVABLE_ON_COMPLETE).i(OBSERVABLE_ON_COMPLETE))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_ON_COMPLETE).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void callsMaybeOnCompleteWhenNoObjectOmited() {
    Maybe.fromCallable(() -> null)
        .doOnComplete(() -> Timber.tag(OBSERVABLE_ON_COMPLETE).i(OBSERVABLE_ON_COMPLETE))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(OBSERVABLE_SUCCESS));
  }

  @Test
  public void callsMaybeOnError() {
    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void doesNotCallOnSuccessWhenError() {
    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnSuccess(o -> Timber.e(OBSERVABLE_SUCCESS))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_SUCCESS).getTag(), equalTo(DEFAULT_NOT_FOUND_TREE_NODE));
  }

  @Test
  public void doesNotCallOnSuccessWhenErrorAndErrorInHookErrorHandle() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, getErrorHandlingActionWithErrorInside()));

    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void callsHookErrorHandleWhenNoDoOnError() {
    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsIt() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, getErrorHandlingActionWithErrorInside()));

    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .subscribe(o -> {
        });

    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }

  @Test
  public void callsHookOnErrorAndMaybeOnError() {
    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(ENTERED_HOOK_ERROR_HANDLE).getTag(), equalTo(ENTERED_HOOK_ERROR_HANDLE));
    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
  }

  @Test
  public void catchesHookOnErrorCallbackExceptionAndLogsItAndCallsMaybeOnError() {
    //noinspection unchecked
    RxJavaPlugins.setOnMaybeSubscribe((Maybe, MaybeObserver) ->
        new MaybeObserverWithErrorHandling(MaybeObserver, getErrorHandlingActionWithErrorInside()));

    Maybe.fromCallable(() -> {
      throw new Exception(OBSERVABLE_ACTION_EXCEPTION);
    })
        .doOnError(throwable -> Timber.tag(OBSERVABLE_DO_ON_ERROR).i(OBSERVABLE_DO_ON_ERROR))
        .doOnSuccess(o -> assertThat("Executed OnSuccess", equalTo("Didn't execute OnSuccess")))
        .subscribe(o -> {
        });

    assertThat(getLogTreeNodeWithTag(OBSERVABLE_DO_ON_ERROR).getTag(), equalTo(OBSERVABLE_DO_ON_ERROR));
    assertThat(getLogTreeExceptionDetailMessage(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK), equalTo(EXCEPTION_WHILE_HANDLING_ERROR_WITH_HOOK));
  }
}
