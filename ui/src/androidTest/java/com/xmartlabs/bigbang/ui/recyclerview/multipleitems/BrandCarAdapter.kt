package com.xmartlabs.bigbang.ui.recyclerview.multipleitems

import android.support.annotation.MainThread
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xmartlabs.bigbang.ui.common.recyclerview.BaseRecyclerViewAdapter
import com.xmartlabs.bigbang.ui.common.recyclerview.SimpleItemRecycleItemType
import com.xmartlabs.bigbang.ui.common.recyclerview.SingleItemBaseViewHolder
import com.xmartlabs.bigbang.ui.recyclerview.common.Brand
import com.xmartlabs.bigbang.ui.recyclerview.common.Car
import com.xmartlabs.bigbang.ui.test.R

class BrandCarAdapter : BaseRecyclerViewAdapter() {
  private val carItemType = object : SimpleItemRecycleItemType<Car, CarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup) = CarViewHolder(inflateView(parent, R.layout.item_single))
  }
  
  private val brandItemType = object : SimpleItemRecycleItemType<Brand, BrandViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup) = BrandViewHolder(inflateView(parent, R.layout.item_single))
  }

  @MainThread
  fun setItems(brands: List<Brand>) {
    brands
        .forEach {
          addItem(brandItemType, it)
          it.cars.forEach { addItem(carItemType, it) }
        }
    notifyDataSetChanged()
  }

  class BrandViewHolder(view: View) : SingleItemBaseViewHolder<Brand>(view) {
    var title: TextView = view.findViewById(android.R.id.title)
  
    override fun bindItem(item: Brand) {
      super.bindItem(item)
      title.text = item.name
    }
  }

  class CarViewHolder(view: View) : SingleItemBaseViewHolder<Car>(view) {
    var title: TextView = view.findViewById(android.R.id.title)

    override fun bindItem(item: Car) {
      super.bindItem(item)
      title.text = item.model
    }
  }
}
