package com.xmartlabs.bigbang.ui.common.recyclerview

import android.view.View
import androidx.annotation.CallSuper

/**
 * View holder for a single item [T].
 * @param <T> The type of the single item.
 */
open class SingleItemBaseViewHolder<in T> constructor(
    view: View,
    clickListener: ((T) -> Unit)? = null
) : BaseViewHolder(view), BindingItemViewHolder<T> {
  private val onClickListener:  ((T) -> Unit)? = clickListener

  @CallSuper
  override fun bindItem(item: T) {
    onClickListener?.let { listener -> itemView.setOnClickListener { listener(item) } }
  }
}
