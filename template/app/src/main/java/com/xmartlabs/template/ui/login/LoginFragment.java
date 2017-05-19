package com.xmartlabs.template.ui.login;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.xmartlabs.template.R;
import com.xmartlabs.template.TemplateApplication;
import com.xmartlabs.template.ui.Henson;
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
  public void setIsLoading(boolean loading) {
    loginButton.setVisibility(loading ? View.GONE : View.VISIBLE);
    progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
  }

  @Override
  public void gotoRecyclerExampleActivity() {
    Intent intent = Henson.with(TemplateApplication.getContext())
        .gotoRecyclerExampleActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    startActivity(intent);
  }

  @OnClick(R.id.log_in_button)
  void onLoginButtonClicked() {
    presenter.loginButtonClicked();
  }
}
