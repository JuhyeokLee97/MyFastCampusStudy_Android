package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_list

data class ChatItemModel(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
) {
    constructor() : this("", "", "", 0)
}