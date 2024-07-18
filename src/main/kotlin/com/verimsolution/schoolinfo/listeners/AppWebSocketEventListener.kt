package com.verimsolution.schoolinfo.listeners

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class AppWebSocketEventListener {

    @EventListener
    fun handlerWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        // TODO: Implement this method
    }
}