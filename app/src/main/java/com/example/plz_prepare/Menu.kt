package com.example.plz_prepare

import android.os.Parcel
import android.os.Parcelable

// 주문 할 음식의 이름, 가격, 음식 설명을 가지는 class, Firebase realtime database에 저장을 하기 위해 data class로 선언
data class Menu(var Fname: String? = null, var Fprice: Int? = 0, var Fexplain: String? = null) :
    Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(Fname)
        writeValue(Fprice)
        writeString(Fexplain)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Menu> = object : Parcelable.Creator<Menu> {
            override fun createFromParcel(source: Parcel): Menu = Menu(source)
            override fun newArray(size: Int): Array<Menu?> = arrayOfNulls(size)
        }
    }
}