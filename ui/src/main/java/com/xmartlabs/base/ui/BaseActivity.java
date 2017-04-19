package com.xmartlabs.base.ui;

import android.content.Context;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.trello.rxlifecycle2.components.RxActivity;
import com.xmartlabs.base.core.Injector;

import bullet.ObjectGraph;

/**
 * A base Activity that inherits from {@link RxActivity} and performs {@link Dart} and {@link ObjectGraph} injections
 * in the {@link #onCreate(Bundle)} lifecycle method.
 */
public abstract class BaseActivity extends RxActivity {
  protected Context getContext() {
    return this;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Dart.inject(this);
    Injector.inject(this);
  }
}
