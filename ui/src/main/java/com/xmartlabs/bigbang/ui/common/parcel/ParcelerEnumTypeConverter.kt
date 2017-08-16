package com.xmartlabs.bigbang.ui.common.parcel

import org.parceler.ParcelConverter
import java.util.*

@Suppress("unused")
open class ParcelerEnumTypeConverter<T : Enum<T>>(val clazz: Class<T>) : ParcelConverter<T> {
  override fun toParcel(input: T?, parcel: android.os.Parcel) {
    parcel.writeInt(input?.ordinal ?: -1)
  }

  override fun fromParcel(parcel: android.os.Parcel): T? {
    val ordinal = parcel.readInt()
    if (ordinal == -1) {
      return null
    }
    return EnumSet.allOf(clazz)
        .filter { it.ordinal == ordinal }
        .firstOrNull()
  }
}
