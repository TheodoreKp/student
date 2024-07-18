package com.verimsolution.schoolinfo.controllers.websockets

import com.verimsolution.schoolinfo.requests.MessageRequest
import com.verimsolution.schoolinfo.services.MessageService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val messageService: MessageService,
    private val messagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping("/chat")
    fun processMessage(@Payload message: MessageRequest) {
        val response = messageService.save(message)
        messagingTemplate.convertAndSendToUser(message.receiver,"/queue/messages", response)
    }
}
