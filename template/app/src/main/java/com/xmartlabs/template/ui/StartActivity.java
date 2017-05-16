package com.xmartlabs.template.ui;

import android.content.Intent;
import android.os.Bundle;

import com.f2prateek.dart.HensonNavigable;
import com.xmartlabs.bigbang.ui.BaseAppCompatActivity;
import com.xmartlabs.template.controller.SessionController;

import javax.inject.Inject;

@HensonNavigable
public class StartActivity extends BaseAppCompatActivity {
  @Inject
  SessionController sessionController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //TODO: handle on Start activity flow. Usually, you should check if user is logged in and redirect accordingly
    Intent intent = Henson.with(getContext())
        .gotoOnboardingActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    startActivity(intent);
    finish();
  }
}
