package com.example.plz_prepare

import android.os.Parcel
import android.os.Parcelable

class CheckingRoute(
    val Category: String?,
    val Uid: String?,
    val Number: Int,
    var State: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(Category)
        writeString(Uid)
        writeInt(Number)
        writeString(State)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CheckingRoute> =
            object : Parcelable.Creator<CheckingRoute> {
                override fun createFromParcel(source: Parcel): CheckingRoute = CheckingRoute(source)
                override fun newArray(size: Int): Array<CheckingRoute?> = arrayOfNulls(size)
            }
    }
}