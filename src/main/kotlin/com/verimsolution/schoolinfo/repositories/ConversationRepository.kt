package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Conversation
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ConversationRepository : MongoRepository<Conversation, String> {
    fun findBySenderAndReceiver(sender: String, receiver: String): Optional<Conversation>
    fun findByChatId(chatId: String): Optional<Conversation>
    fun findAllByReceiver(receiver: String): List<Conversation>
}
