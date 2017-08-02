package com.xmartlabs.bigbang.ui.recyclerview.singleitem

import com.xmartlabs.bigbang.ui.recyclerview.common.ListActivity

class SingleItemActivity : ListActivity<CarAdapter>() {
  override fun createAdapter() = CarAdapter()
}
