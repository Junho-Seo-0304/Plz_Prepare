package com.example.plz_prepare

import android.os.Parcel
import android.os.Parcelable

// 주문상태의 정보를 가지고 있는 class, 레스토랑의 카테고리, 레스토랑의 UID, 주문의 번호표, 현재 주문의 상태를 가지고 있다.
class CheckingRoute(
    val Category: String?,
    val Uid: String?,
    val Number: String?,
    var State: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(Category)
        writeString(Uid)
        writeString(Number)
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