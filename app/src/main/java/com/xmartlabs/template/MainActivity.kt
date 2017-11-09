package com.xmartlabs.template

import android.os.Bundle
import com.f2prateek.dart.HensonNavigable
import com.xmartlabs.bigbang.ui.mvp.BaseMvpAppCompatActivity
import com.xmartlabs.bigbang.ui.mvp.BaseMvpPresenter
import com.xmartlabs.bigbang.ui.mvp.MvpView

import kotlinx.android.synthetic.main.activity_main.*

@HensonNavigable
class MainActivity : BaseMvpAppCompatActivity<MvpView, BaseMvpPresenter<MvpView>>() {
  override var presenter: BaseMvpPresenter<MvpView>
    get() = BaseMvpPresenter()
    set(value) {}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
  }
}
