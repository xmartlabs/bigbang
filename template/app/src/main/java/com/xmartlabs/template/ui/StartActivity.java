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

    sessionController.getSession()
        .executeIfPresent(session -> {
          //TODO: handle on start activity with session
        })
        .executeIfAbsent(() -> {
          //TODO: handle on start activity no session
          Intent intent = Henson.with(getContext())
              .gotoOnboardingActivity()
              .build()
              .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
        });
  }
}
