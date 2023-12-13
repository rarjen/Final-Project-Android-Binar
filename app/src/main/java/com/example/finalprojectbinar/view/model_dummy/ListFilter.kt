package com.example.finalprojectbinar.view.model_dummy

import android.os.Parcel
import android.os.Parcelable
import com.example.finalprojectbinar.model.DataCategories

sealed class ListFilter {
    data class HeaderItem(val text: String):ListFilter()
    data class CheckboxItem(
        val id: Int?,
        val text: String?,
        val tag: String?
        ):ListFilter(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(text)
            parcel.writeString(tag)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CheckboxItem> {
            override fun createFromParcel(parcel: Parcel): CheckboxItem {
                return CheckboxItem(parcel)
            }

            override fun newArray(size: Int): Array<CheckboxItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}
