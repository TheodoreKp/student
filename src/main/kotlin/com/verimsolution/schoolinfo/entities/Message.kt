package com.verimsolution.schoolinfo.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(collection = "messages")
data class Message(
    @field:Id
    val id: ObjectId,
    val text: String,
    @field:Field("image_url")
    val imageUrl: String?,
    @field:Field("chat_id")
    val chatId: String,
    val sender: String,
    val receiver: String,
    @field:Field("created_at")
    val createdAt: Date
) {

    constructor() : this(
        id = ObjectId.get(),
        text = "",
        imageUrl = null,
        chatId = "",
        sender = "",
        receiver = "",
        createdAt = Date()
    )
}
