package com.verimsolution.schoolinfo.repositories

import com.verimsolution.schoolinfo.entities.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String>{
    fun findByChatId(chatId: String): List<Message>
}
