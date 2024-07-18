package com.verimsolution.schoolinfo.handlers

import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.socket.messaging.SessionDisconnectEvent


class WebSocketDisconnectHandler<S>(
    private val messagingTemplate: SimpMessageSendingOperations
) : ApplicationListener<SessionDisconnectEvent> {

    override fun onApplicationEvent(event: SessionDisconnectEvent) {
        val id = event.sessionId
//        this.repository.findById(id).ifPresent { user ->
//            this.repository.deleteById(id)
//            messagingTemplate.convertAndSend(
//                "/topic/friends/signout",
//                listOf(user.getUsername())
//            )
//        }
    }

//    fun WebSocketDisconnectHandler(
//        messagingTemplate: SimpMessageSendingOperations?,
//        repository: ActiveWebSocketUserRepository
//    ) {
//        super()
//        this.messagingTemplate = messagingTemplate!!
//        repository = repository
//    }
}