package com.verimsolution.schoolinfo.responses

import java.util.*

data class MessageResponse(
    val id: String,
    val text: String,
    val imageUrl: String?,
    val chatId: String,
    val sender: String,
    val receiver: String,
    val createdAt: Date,
)
