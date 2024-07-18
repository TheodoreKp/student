package com.verimsolution.schoolinfo.responses

import java.util.*

data class ConversationResponse(
    val id: String,
    val sender: Any,
    val receiver: Any,
    val chatId: String,
    val lastMessage: String,
    val unreadCount: Int,
    val createdAt: Date,
    val updatedAt: Date
)
