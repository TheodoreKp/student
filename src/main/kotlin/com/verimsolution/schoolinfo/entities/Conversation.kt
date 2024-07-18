package com.verimsolution.schoolinfo.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversations")
data class Conversation(
    @Id
    val id: ObjectId,
    val sender: String,
    val receiver: String,
    @Field("chat_id")
    val chatId: String,
    val lastMessage: String,
    @Field("unread_count")
    val unreadCount: Int,
    @Field("created_at")
    val createdAt: Date,
    @Field("updated_at")
    val updatedAt: Date
) {
    constructor() : this(
        id = ObjectId.get(),
        sender = "",
        receiver = "",
        chatId = "",
        lastMessage = "",
        unreadCount = 0,
        createdAt = Date(),
        updatedAt = Date()
    )
}
