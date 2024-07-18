package com.verimsolution.schoolinfo.services

import com.verimsolution.schoolinfo.responses.ContactResponse
import com.verimsolution.schoolinfo.responses.ConversationResponse
import java.util.*

interface ConversationService {

    fun listProfessorConversation(): List<ConversationResponse>
    fun listStudentConversation(): List<ConversationResponse>
    fun showConversation(id: String): ConversationResponse
    fun showConversationId(senderId: String, receiverId: String, isNewConversation: Boolean): Optional<String>
    fun listConversationContacts(): List<ContactResponse>
    fun listStudentConversationContacts(): List<ContactResponse>
    fun updateConversation(senderId: String, receiverId: String, text: String)
}
