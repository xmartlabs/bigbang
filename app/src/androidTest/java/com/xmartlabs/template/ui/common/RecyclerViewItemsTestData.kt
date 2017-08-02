package com.xmartlabs.template.ui.common

import android.content.Intent
import android.support.v4.util.Pair

data class RecyclerViewItemsTestData(
  var content: List<Pair<Int, Int>>,
  var serviceUrl: String,
  var jsonResponsePath: String,
  var intent: Intent
)
