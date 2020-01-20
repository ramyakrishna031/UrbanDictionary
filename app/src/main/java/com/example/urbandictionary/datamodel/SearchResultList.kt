package com.example.urbandictionary.datamodel


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultList(
    val list:ArrayList<SearchResult>
):Parcelable

@Parcelize
data class SearchResult(
    val definition:String,
    @SerializedName("thumbs_up")
    val thumbsUp: Int,
    @SerializedName("thumbs_down")
    val thumbsDown: Int
):Parcelable

