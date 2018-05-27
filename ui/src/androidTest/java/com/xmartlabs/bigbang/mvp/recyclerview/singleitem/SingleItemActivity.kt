package com.xmartlabs.bigbang.mvp.recyclerview.singleitem

import com.xmartlabs.bigbang.mvp.recyclerview.common.ListActivity

class SingleItemActivity : ListActivity<CarAdapter>() {
  override fun createAdapter() = CarAdapter()
}
