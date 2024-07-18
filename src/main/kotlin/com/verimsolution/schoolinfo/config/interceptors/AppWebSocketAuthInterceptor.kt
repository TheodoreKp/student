package com.verimsolution.schoolinfo.config.interceptors

import com.verimsolution.schoolinfo.repositories.UserRepository
import com.verimsolution.schoolinfo.utils.JwtUtils
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component


@Component
class AppWebSocketAuthInterceptor(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        val cmd = accessor!!.command
        var jwt: String? = null
        if (StompCommand.CONNECT == cmd || StompCommand.SEND == cmd) {
            val requestTokenHeader = accessor.getFirstNativeHeader("Authorization")
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwt = requestTokenHeader.substring(7)
            }
            if (!jwtUtils.validateToken(jwt!!)) {
                throw Exception("Invalid token")
            }
        }
        return message
    }

}