package com.xmartlabs.template.ui.common;

import android.content.Context;
import android.support.annotation.StringRes;

import com.xmartlabs.bigbang.ui.mvp.MvpView;

import io.reactivex.annotations.NonNull;

public interface TemplateView extends MvpView {
  void showError(@StringRes int message);
  void showError(@StringRes int message, @StringRes int title);
  void showError(@StringRes int message, @StringRes int title, @StringRes int buttonTitle);
  void showError(@NonNull Throwable error, @StringRes Integer message);
  String getString(@StringRes int stringRes);
  boolean isViewAlive();
  Context getContext();
}
