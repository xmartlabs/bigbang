package com.xmartlabs.bigbang.ui.recyclerview.multipleitems

import com.xmartlabs.bigbang.ui.recyclerview.common.Brand
import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity

class MultipleItemActivity : ListActivity<BrandCarAdapter>() {
  override fun createAdapter() = BrandCarAdapter()
  fun setItems(brands: List<Brand>) = adapter.setItems(brands)
}
