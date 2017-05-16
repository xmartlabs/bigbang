package com.xmartlabs.template.ui.login;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.R;
import com.xmartlabs.template.ui.common.TemplateFragment;

import butterknife.BindView;
import butterknife.OnClick;

@FragmentWithArgs
public class LoginFragment extends TemplateFragment<LoginView, LoginPresenter> implements LoginView {
  @BindView(R.id.log_in_button)
  Button loginButton;
  @BindView(R.id.progress_bar)
  ProgressBar progressBar;

  @NonNull
  private final LoginPresenter presenter = LoginPresenter.builder().build();

  @NonNull
  @Override
  protected LoginPresenter createPresenter() {
    return presenter;
  }

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.fragment_login;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void setIsLoading(boolean loading) {
    loginButton.setVisibility(loading ? View.GONE : View.VISIBLE);
    progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  @OnClick(R.id.log_in_button)
  void onLoginButtonClicked() {
    presenter.loginButtonClicked();
  }
}
