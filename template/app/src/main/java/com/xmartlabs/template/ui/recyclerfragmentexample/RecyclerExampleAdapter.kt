package com.xmartlabs.template.ui.recyclerfragmentexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.xmartlabs.template.R

internal class RecyclerExampleAdapter(private val items: List<String>)
  : RecyclerView.Adapter<RecyclerExampleAdapter.RecyclerExampleViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerExampleViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_example, parent, false)
    return RecyclerExampleViewHolder(view)
  }

  override fun onBindViewHolder(holder: RecyclerExampleViewHolder, position: Int) = holder.bind(items[position])

  override fun getItemCount() = items.size

  internal class RecyclerExampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(string: String) = (itemView as? TextView)?.let { it.text = string } ?: Unit
  }
}
