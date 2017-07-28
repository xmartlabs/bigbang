package com.xmartlabs.template.ui.recyclerfragmentexample

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.bigbang.ui.common.recyclerview.RecyclerViewEmptySupport
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.TemplateFragment
import java.util.Locale

import butterknife.BindView

@FragmentWithArgs
class RecyclerExampleFragment : TemplateFragment<RecyclerExampleView, RecyclerExamplePresenter>(), RecyclerExampleView {
  @BindView(R.id.recyclerView)
  internal lateinit var recyclerView: RecyclerViewEmptySupport

  override fun createPresenter(): RecyclerExamplePresenter {
    return RecyclerExamplePresenter()
  }

  @LayoutRes
  override fun getLayoutResId(): Int {
    return R.layout.fragment_recycler_example
  }

  override fun setup() {
    val strings = listOf(1..39)
        .map { String.format(Locale.getDefault(), "Item %d", it) }

    recyclerView.adapter = RecyclerExampleAdapter(strings)
    recyclerView.layoutManager = LinearLayoutManager(context)
  }
}
