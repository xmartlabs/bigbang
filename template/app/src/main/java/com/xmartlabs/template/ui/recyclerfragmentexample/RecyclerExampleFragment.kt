package com.xmartlabs.template.ui.recyclerfragmentexample

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.TemplateFragment
import java.util.*
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_recycler_example.*

@FragmentWithArgs
class RecyclerExampleFragment : TemplateFragment<RecyclerExampleView, RecyclerExamplePresenter>(), RecyclerExampleView {
  @Inject
  override lateinit var presenter: RecyclerExamplePresenter

  @LayoutRes
  override val layoutResId = R.layout.fragment_recycler_example

  override fun setup() {
    val strings = listOf(1..39)
        .flatten()
        .map { String.format(Locale.getDefault(), "Item %d", it) }

    recyclerView.adapter = RecyclerExampleAdapter(strings)
    recyclerView.layoutManager = LinearLayoutManager(context)
  }
}
