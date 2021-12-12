package com.example.myfastcampusstudy_android.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("itemId") val id: Long,
    @SerializedName("tittle") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("coverSmallUrl") val coverSmallUrl: String
)
