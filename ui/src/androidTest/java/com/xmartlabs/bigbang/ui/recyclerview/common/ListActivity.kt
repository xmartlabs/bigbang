package com.xmartlabs.bigbang.ui.recyclerview.common

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xmartlabs.bigbang.ui.test.R
import com.xmartlabs.bigbang.ui.test.R.layout.activity_with_list

abstract class ListActivity<T : RecyclerView.Adapter<*>> : Activity() {
  private lateinit var recyclerView: RecyclerView

  lateinit var adapter: T

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activity_with_list)
    recyclerView = findViewById(R.id.recycler_view)
    adapter = createAdapter()
    setupRecyclerViewAdapter()
  }

  private fun setupRecyclerViewAdapter() {
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
  }

  abstract fun createAdapter(): T
}
