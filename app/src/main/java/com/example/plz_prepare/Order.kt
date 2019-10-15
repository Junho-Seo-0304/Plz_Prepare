package com.example.plz_prepare

import android.os.Parcel
import android.os.Parcelable

class Order(val food: Menu?, var num: Int) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<Menu>(Menu::class.java.classLoader),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(food, 0)
        writeInt(num)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Order> = object : Parcelable.Creator<Order> {
            override fun createFromParcel(source: Parcel): Order = Order(source)
            override fun newArray(size: Int): Array<Order?> = arrayOfNulls(size)
        }
    }
}