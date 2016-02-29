package com.xmartlabs.template.ui;

import android.content.Intent;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.xmartlabs.template.R;
import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.model.AuthResponse;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by santiago on 31/08/15.
 */
@FragmentWithArgs
public class WelcomeFragment extends BaseFragment {
  @Inject
  AuthController authController;

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_welcome;
  }

  @OnClick(R.id.log_in_button)
  @SuppressWarnings("unused")
  void logIn() {
    authController.login()
        .toObservable()
        .compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW))
        .toSingle()
        .subscribe(
            authResponse -> {
              Intent intent = Henson.with(getActivity()).gotoMainActivity().build();
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
            },
            throwable -> showAlertError(R.string.message_error_service_call)
        );
  }
}
