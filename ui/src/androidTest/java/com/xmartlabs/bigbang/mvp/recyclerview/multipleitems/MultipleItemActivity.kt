package com.xmartlabs.bigbang.mvp.recyclerview.multipleitems

import com.xmartlabs.bigbang.mvp.recyclerview.common.Brand
import com.xmartlabs.bigbang.mvp.recyclerview.common.ListActivity

class MultipleItemActivity : ListActivity<BrandCarAdapter>() {
  override fun createAdapter() = BrandCarAdapter()
  fun setItems(brands: List<Brand>) = adapter.setItems(brands)
}
