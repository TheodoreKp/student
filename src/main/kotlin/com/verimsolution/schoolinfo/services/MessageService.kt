package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.requests.MessageRequest
import com.verimsolution.schoolinfo.responses.MessageResponse

interface MessageService {
    fun list(sender: String, receiver: String): List<MessageResponse>
    fun save(request: MessageRequest): MessageResponse
}
