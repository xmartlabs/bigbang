package com.xmartlabs.template.ui;

import android.content.Context;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.xmartlabs.template.BaseProjectApplication;
import com.trello.rxlifecycle.components.RxActivity;

/**
 * Created by santiago on 31/08/15.
 */
public abstract class BaseActivity extends RxActivity {
    protected Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);

        BaseProjectApplication.getContext().inject(this);
    }
}
