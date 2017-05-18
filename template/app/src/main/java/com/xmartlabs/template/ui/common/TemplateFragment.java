package com.xmartlabs.template.ui.common;

import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xmartlabs.bigbang.ui.mvp.BaseMvpFragment;
import com.xmartlabs.bigbang.ui.mvp.MvpPresenter;
import com.xmartlabs.template.R;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.CompositeException;

public abstract class TemplateFragment<V extends TemplateView, P extends MvpPresenter<V>>
    extends BaseMvpFragment<V, P> implements TemplateView {
  @Override
  public void showError(@StringRes int message) {
    showError(message, R.string.error);
  }

  @Override
  public void showError(@StringRes int message, @StringRes int title) {
    showError(message, title, android.R.string.ok);
  }

  @Override
  public void showError(@StringRes int message, @StringRes int title, @StringRes int buttonTitle) {
    if (isViewAlive()) {
      new MaterialDialog.Builder(getContext())
          .title(title)
          .content(message)
          .positiveText(buttonTitle)
          .build()
          .show();
    }
  }

  @Override
  public void showError(@NonNull Throwable error, @StringRes Integer message) {
    if (error instanceof CompositeException) {
      error = ((CompositeException) error).getExceptions().get(0);
    }
    if (error instanceof CancellationException) {
      return;
    }
    if (error instanceof IOException) {
      showError(R.string.check_your_internet_connection, R.string.no_internet_connection);
    } else if (message == null) {
      showError(R.string.error_service_call_generic);
    } else {
      showError(message);
    }
  }

  @Override
  public boolean isViewAlive() {
    return isAdded() && getActivity() != null;
  }
}
