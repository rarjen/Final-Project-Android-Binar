package com.example.finalprojectbinar.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class DataFilter:Parcelable{
  @kotlinx.parcelize.Parcelize
   data class Category(val categoryId: String?):DataFilter()
   @kotlinx.parcelize.Parcelize
   data class Level(val level: String?):DataFilter()
}