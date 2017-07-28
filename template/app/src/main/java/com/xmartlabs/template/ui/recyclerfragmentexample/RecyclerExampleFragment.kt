package com.xmartlabs.template.ui.recyclerfragmentexample

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import butterknife.BindView
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.bigbang.ui.common.recyclerview.RecyclerViewEmptySupport
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.TemplateFragment
import java.util.*
import javax.inject.Inject

@FragmentWithArgs
class RecyclerExampleFragment : TemplateFragment<RecyclerExampleView, RecyclerExamplePresenter>(), RecyclerExampleView {
  @BindView(R.id.recyclerView)
  internal lateinit var recyclerView: RecyclerViewEmptySupport
  
  @Inject
  internal lateinit var presenter: RecyclerExamplePresenter
  
  override fun createPresenter() = presenter

  @LayoutRes
  override fun getLayoutResId() = R.layout.fragment_recycler_example

  override fun setup() {
    val strings = listOf(1..39)
        .flatten()
        .map { String.format(Locale.getDefault(), "Item %d", it) }

    recyclerView.adapter = RecyclerExampleAdapter(strings)
    recyclerView.layoutManager = LinearLayoutManager(context)
  }
}
