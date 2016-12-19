package com.xmartlabs.template.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.f2prateek.dart.Dart;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmartlabs.template.BaseProjectApplication;

/**
 * Created by diegomedina24 on 12/16/16.
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity {
  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);
    BaseProjectApplication.getContext().inject(this);
  }

  /**
   * Removes the given fragment from the view.
   *
   * @param fragment the fragment to be removed
   */
  public void removeFragment(@NonNull Fragment fragment) {
    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
  }

  /** Hides the keyboard, if visible */
  protected void hideKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
    View currentFocus = this.getCurrentFocus();
    if (currentFocus != null) {
      inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
  }
}
