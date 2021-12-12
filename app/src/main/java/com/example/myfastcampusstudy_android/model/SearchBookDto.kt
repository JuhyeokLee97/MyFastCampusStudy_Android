package com.example.myfastcampusstudy_android.model

import com.google.gson.annotations.SerializedName

data class SearchBookDto(
    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<Book>
)
