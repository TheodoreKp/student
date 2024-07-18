package com.verimsolution.schoolinfo.handlers

import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.socket.messaging.SessionConnectEvent
import java.util.*


class WebSocketConnectHandler<S>(
    private val messagingTemplate: SimpMessageSendingOperations
) : ApplicationListener<SessionConnectEvent> {

    override fun onApplicationEvent(event: SessionConnectEvent) {
        val headers = event.message.headers
        val user = SimpMessageHeaderAccessor.getUser(headers) ?: return
        val id = SimpMessageHeaderAccessor.getSessionId(headers)
//        this.repository.save(ActiveWebSocketUser(id, user.name, Calendar.getInstance()))
        messagingTemplate.convertAndSend("/topic/friends/signin", listOf(user.name))
    }

//    fun WebSocketConnectHandler(
//        messagingTemplate: SimpMessageSendingOperations?,
//        repository: ActiveWebSocketUserRepository
//    ) {
//        super()
//        this.messagingTemplate = messagingTemplate!!
//        repository = repository
//    }
}