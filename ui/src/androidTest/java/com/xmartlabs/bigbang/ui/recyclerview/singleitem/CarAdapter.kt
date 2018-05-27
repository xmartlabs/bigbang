package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseRecyclerViewAdapter
import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseViewHolder
import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import com.xmartlabs.bigbang.ui.test.R

class CarAdapter : SingleItemBaseRecyclerViewAdapter<Car, CarAdapter.CarViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup) =
      CarViewHolder(inflateView(parent, R.layout.item_single))

  class CarViewHolder(view: View) : SingleItemBaseViewHolder<Car>(view) {
    var title: TextView = view.findViewById(android.R.id.title)

    override fun bindItem(item: Car) {
      super.bindItem(item)
      title.text = item.model
    }
  }
}
