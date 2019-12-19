package com.xmartlabs.bigbang.ui.common.recyclerview

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  /**
   * Returns the context the view is running in, through which it can
   * access the current theme, resources, etc.
   */
  protected val context: Context = itemView.context

  /** Resources instance for the application's package
   */
  protected val resources: Resources = context.resources
}
