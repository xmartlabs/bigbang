package com.xmartlabs.template.ui.login

import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import butterknife.BindView
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.App
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.Henson
import com.xmartlabs.template.ui.common.TemplateFragment
import javax.inject.Inject

@FragmentWithArgs
class LoginFragment : TemplateFragment<LoginView, LoginPresenter>(), LoginView {
  @BindView(R.id.log_in_button)
  internal lateinit var loginButton: Button
  @BindView(R.id.progress_bar)
  internal lateinit var progressBar: ProgressBar
  
  @Inject
  internal lateinit var presenter: LoginPresenter
  
  override fun createPresenter() = presenter
  
  @LayoutRes
  override fun getLayoutResId() = R.layout.fragment_login
  
  override fun setup() {
    loginButton.setOnClickListener { presenter.loginButtonClicked() }
  }
  
  override fun setIsLoading(loading: Boolean) {
    loginButton.visibility = if (loading) View.GONE else View.VISIBLE
    progressBar.visibility = if (loading) View.VISIBLE else View.GONE
  }
  
  override fun gotoRecyclerExampleActivity() {
    val intent = Henson.with(App.context)
        .gotoRecyclerExampleActivity()
        .build()
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
  }
}
