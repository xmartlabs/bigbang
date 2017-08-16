package com.xmartlabs.template.ui.recyclerfragmentexample

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.template.R
import com.xmartlabs.template.ui.common.TemplateFragment
import kotlinx.android.synthetic.main.fragment_recycler_example.*
import java.util.Locale
import javax.inject.Inject

@FragmentWithArgs
class RecyclerExampleFragment : TemplateFragment<RecyclerExampleView, RecyclerExamplePresenter>(), RecyclerExampleView {
  companion object {
    private const val LIST_COUNT = 39
  }

  @Inject
  override lateinit var presenter: RecyclerExamplePresenter

  @LayoutRes
  override val layoutResId = R.layout.fragment_recycler_example

  override fun setup() {
    val strings = listOf(1..LIST_COUNT)
        .flatten()
        .map { String.format(Locale.getDefault(), "Item %d", it) }

    recyclerView.adapter = RecyclerExampleAdapter(strings)
    recyclerView.layoutManager = LinearLayoutManager(context)
  }
}
