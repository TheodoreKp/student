package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.schoolinfo.entities.Message
import com.verimsolution.schoolinfo.repositories.MessageRepository
import com.verimsolution.schoolinfo.requests.MessageRequest
import com.verimsolution.schoolinfo.responses.MessageResponse
import com.verimsolution.schoolinfo.services.ConversationService
import com.verimsolution.schoolinfo.services.MessageService
import com.verimsolution.schoolinfo.utils.response
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    private val repository: MessageRepository,
    private val conversationService: ConversationService
) : MessageService {
    override fun list(sender: String, receiver: String): List<MessageResponse> {
        val chatId = conversationService.showConversationId(sender, receiver, false)
        return chatId.map { id -> repository.findByChatId(id).map { it.response() } }.orElse(emptyList())
    }

    override fun save(request: MessageRequest): MessageResponse {
        val chatId = conversationService.showConversationId(request.sender, request.receiver, true).orElseThrow()
        val message = Message()
            .copy(chatId = chatId, text = request.text, sender = request.sender, receiver = request.receiver)
        conversationService.updateConversation(
            senderId = message.sender, receiverId = message.receiver, text = message.text
        )
        return repository.save(message).response()
    }


}
