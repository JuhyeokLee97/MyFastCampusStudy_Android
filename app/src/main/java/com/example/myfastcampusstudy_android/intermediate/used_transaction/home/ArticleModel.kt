package com.example.myfastcampusstudy_android.intermediate.used_transaction.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createdAt: Long,
    val price: String,
    val imageUrl: String
){
    constructor(): this("", "", 0, "", "")
}