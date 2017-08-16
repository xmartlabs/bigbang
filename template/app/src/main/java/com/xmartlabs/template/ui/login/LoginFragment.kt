package com.xmartlabs.template.ui.login

import android.content.Intent
import android.view.View
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.App
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.Henson
import com.xmartlabs.template.ui.common.TemplateFragment
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@FragmentWithArgs
class LoginFragment : TemplateFragment<LoginView, LoginPresenter>(), LoginView {
  @Inject
  override lateinit var presenter: LoginPresenter

  override val layoutResId = R.layout.fragment_login

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
