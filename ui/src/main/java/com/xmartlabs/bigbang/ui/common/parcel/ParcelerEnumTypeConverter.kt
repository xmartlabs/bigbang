package com.xmartlabs.bigbang.ui.common.parcel

import org.parceler.ParcelConverter
import java.util.*

@Suppress("unused")
open class ParcelerEnumTypeConverter<T : Enum<T>>(val clazz: Class<T>) : ParcelConverter<T> {
  override fun toParcel(input: T?, parcel: android.os.Parcel) {
    if (input == null) {
      parcel.writeInt(-1)
    } else {
      parcel.writeInt(1)
      parcel.writeInt(input.ordinal)
    }
  }

  override fun fromParcel(parcel: android.os.Parcel): T? {
    if (parcel.readInt() == -1) {
      return null
    }
    return EnumSet.allOf(clazz)
        .filter { it.ordinal == parcel.readInt() }
        .firstOrNull()
  }
}
