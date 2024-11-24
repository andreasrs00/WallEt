package com.example.wallet.model

import android.os.Parcel
import android.os.Parcelable

data class Categories(
        val imageResId: Int,
        val uang: String,
        val waktu: String,
        val kegiatan: String
        )
        : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(imageResId)
                parcel.writeString(uang)
                parcel.writeString(waktu)
                parcel.writeString(kegiatan)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Categories> {
                override fun createFromParcel(parcel: Parcel): Categories {
                        return Categories(parcel)
                }

                override fun newArray(size: Int): Array<Categories?> {
                        return arrayOfNulls(size)
                }
        }
}
