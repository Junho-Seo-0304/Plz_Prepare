package com.example.plz_prepare

import android.os.Parcel
import android.os.Parcelable

open class Food(val name: String?, val image: Int, val price: Int, val explain: String?) :
    Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeInt(image)
        writeInt(price)
        writeString(explain)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Food> = object : Parcelable.Creator<Food> {
            override fun createFromParcel(source: Parcel): Food = Food(source)
            override fun newArray(size: Int): Array<Food?> = arrayOfNulls(size)
        }
    }
}