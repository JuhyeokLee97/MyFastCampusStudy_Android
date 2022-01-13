package com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_room

data class ChatModel(
    var senderId: String,
    var message: String
) {
    constructor() : this("", "")
}