package com.xmartlabs.template.ui.login

import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.TemplateFragment

import butterknife.BindView
import butterknife.OnClick

@FragmentWithArgs
class LoginFragment : TemplateFragment<LoginView, LoginPresenter>(), LoginView {
  @BindView(R.id.log_in_button)
  internal lateinit var loginButton: Button
  @BindView(R.id.progress_bar)
  internal lateinit var progressBar: ProgressBar

  private val presenter = LoginPresenter()

  override fun createPresenter(): LoginPresenter {
    return presenter
  }

  @LayoutRes
  override fun getLayoutResId(): Int {
    return R.layout.fragment_login
  }

  override fun setIsLoading(loading: Boolean) {
    loginButton.visibility = if (loading) View.GONE else View.VISIBLE
    progressBar.visibility = if (loading) View.VISIBLE else View.GONE
  }

  override fun gotoRecyclerExampleActivity() {
    //    Intent intent = Henson.with(App.getContext())
    //        .gotoRecyclerExampleActivity()
    //        .build()
    //        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    //
    //    startActivity(intent);
  }

  @OnClick(R.id.log_in_button)
  internal fun onLoginButtonClicked() {
    presenter.loginButtonClicked()
  }
}
