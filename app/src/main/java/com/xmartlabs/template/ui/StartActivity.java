package com.xmartlabs.template.ui;

import android.content.Intent;
import android.os.Bundle;

import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.controller.SessionController;
import com.xmartlabs.template.controller.RepoController;
import com.xmartlabs.template.model.Session;

import javax.inject.Inject;

/**
 * Created by santiago on 31/08/15.
 */
public class StartActivity extends BaseActivity {
  @Inject
  AuthController authController;
  @Inject
  RepoController repoController;
  @Inject
  SessionController sessionController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Session session = sessionController.getSession();

    Intent intent;
    if (session == null) {
      intent = Henson.with(getContext()).gotoWelcomeActivity().build();
    } else {
      intent = Henson.with(getContext()).gotoMainActivity().build();
      authController.setLoginInfo(session);
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    getContext().startActivity(intent);
  }
}
