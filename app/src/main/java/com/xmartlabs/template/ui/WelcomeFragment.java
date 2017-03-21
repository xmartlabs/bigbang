package com.xmartlabs.template.ui;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xmartlabs.template.R;
import com.xmartlabs.template.controller.AuthController;
import com.xmartlabs.template.model.LoginRequest;
import com.xmartlabs.template.model.Session;
import com.xmartlabs.template.ui.mvp.BaseMvpFragment;
import com.xmartlabs.template.ui.mvp.BaseMvpPresenter;
import com.xmartlabs.template.ui.mvp.MvpView;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.Single;

/**
 * Created by santiago on 31/08/15.
 */
@FragmentWithArgs
public class WelcomeFragment extends BaseMvpFragment<MvpView, BaseMvpPresenter<MvpView>> {
  @Inject
  AuthController authController;

  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_welcome;
  }

  @OnClick(R.id.log_in_button)
  @SuppressWarnings("unused")
  void logIn() {
    authController.login(LoginRequest.builder().build())
        .toObservable()
        .compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY_VIEW))
        .singleOrError()
        .subscribe(
            session -> {
              Intent intent = Henson.with(getActivity()).gotoMainActivity().build();
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
            },
            throwable -> showAlertError(R.string.message_error_service_call)
        );
  }

  @NonNull
  @Override
  protected BaseMvpPresenter<MvpView> createPresenter() {
    return new BaseMvpPresenter<>();
  }
}
