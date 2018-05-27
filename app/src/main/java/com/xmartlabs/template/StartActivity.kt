package com.xmartlabs.template

import android.content.Intent
import android.os.Bundle

import com.xmartlabs.bigbang.ui.BaseActivity

class StartActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Henson.with(context).gotoMainActivity().build()

    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
  }
}
