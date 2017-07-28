package com.xmartlabs.template.ui

import android.content.Intent
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.xmartlabs.bigbang.ui.BaseAppCompatActivity
import com.xmartlabs.template.controller.SessionController
import javax.inject.Inject

@HensonNavigable
class StartActivity : BaseAppCompatActivity() {
  @Inject
  internal lateinit var sessionController: SessionController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    sessionController.session
        .executeIfPresent { session ->
          //TODO: handle on start activity with session
        }
        .executeIfAbsent {
          //TODO: handle on start activity no session
          val intent = Henson.with(context)
              .gotoOnboardingActivity()
              .build()
              .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(intent)
        }
  }
}
