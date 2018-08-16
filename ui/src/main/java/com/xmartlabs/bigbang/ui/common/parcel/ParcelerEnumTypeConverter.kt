package com.xmartlabs.bigbang.ui.common.parcel

import org.parceler.ParcelConverter
import java.util.EnumSet

@Suppress("unused")
open class ParcelerEnumTypeConverter<T : Enum<T>>(val clazz: Class<T>) : ParcelConverter<T> {
  companion object {
    private const val NULL = -1
  }

  override fun toParcel(input: T?, parcel: android.os.Parcel) {
    parcel.writeInt(input?.ordinal ?: NULL)
  }

  override fun fromParcel(parcel: android.os.Parcel): T? {
    val ordinal = parcel.readInt()
    return EnumSet.allOf(clazz).firstOrNull { it.ordinal == ordinal }
  }
}
