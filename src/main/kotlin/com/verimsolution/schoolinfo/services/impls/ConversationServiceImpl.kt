package com.verimsolution.schoolinfo.services.impls

import com.verimsolution.schoolinfo.entities.Conversation
import com.verimsolution.schoolinfo.entities.Professor
import com.verimsolution.schoolinfo.entities.Student
import com.verimsolution.schoolinfo.exceptions.ProfessorNotFoundException
import com.verimsolution.schoolinfo.exceptions.StudentNotFoundException
import com.verimsolution.schoolinfo.repositories.ConversationRepository
import com.verimsolution.schoolinfo.repositories.ProfessorRepository
import com.verimsolution.schoolinfo.repositories.StudentRepository
import com.verimsolution.schoolinfo.responses.ContactResponse
import com.verimsolution.schoolinfo.responses.ConversationResponse
import com.verimsolution.schoolinfo.services.ConversationService
import com.verimsolution.schoolinfo.utils.contactResponse
import com.verimsolution.schoolinfo.utils.response
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ConversationServiceImpl(
    private val repository: ConversationRepository,
    private val studentRepository: StudentRepository,
    private val professorRepository: ProfessorRepository
) : ConversationService {
    override fun listProfessorConversation(): List<ConversationResponse> {
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        return repository.findAllByReceiver(professor.id.toHexString()).map { it.response() }
    }

    override fun listStudentConversation(): List<ConversationResponse> {
        val student = getStudent(SecurityContextHolder.getContext().authentication.name)
        return repository.findAllByReceiver(student.id.toHexString()).map { it.response() }
    }

    override fun showConversation(id: String): ConversationResponse {
        TODO("Not yet implemented")
    }

    override fun showConversationId(
        senderId: String, receiverId: String, isNewConversation: Boolean
    ): Optional<String> {
        return repository.findBySenderAndReceiver(senderId, receiverId)
            .map { it.chatId }
            .or {
                if (isNewConversation) {
                    val messageId = createMessage(senderId, receiverId)
                    Optional.of(messageId)
                }
                Optional.empty()
            }
    }

    override fun listConversationContacts(): List<ContactResponse> {
        val contacts = mutableListOf<ContactResponse>()
        val professor = getProfessor(SecurityContextHolder.getContext().authentication.name)
        val students = studentRepository.findAll().filter { professor.classrooms.contains(it.classroom) }.map {
            it.contactResponse()
        }
        contacts.addAll(students)
        val professors = professorRepository.findAll().filter { it != professor }.map { it.contactResponse() }
        contacts.addAll(professors)
        return contacts
    }

    override fun listStudentConversationContacts(): List<ContactResponse> {
        val contacts = mutableListOf<ContactResponse>()
        val student = getStudent(SecurityContextHolder.getContext().authentication.name)
        val students = studentRepository.findAll().filter { student.classroom == it.classroom }.map {
            it.contactResponse()
        }
        contacts.addAll(students)
        val professors = professorRepository.findAll().filter { it.classrooms.contains(student.classroom) }.map {
            it.contactResponse()
        }
        contacts.addAll(professors)
        return contacts
    }

    override fun updateConversation(senderId: String, receiverId: String, text: String) {
        val conversation1 = repository.findBySenderAndReceiver(senderId, receiverId).orElseThrow {
            IllegalArgumentException("Conversation with sender $senderId and receiver $receiverId is not found.")
        }
        val conversation2 = repository.findBySenderAndReceiver(receiverId, senderId).orElseThrow {
            IllegalArgumentException("Conversation with sender $receiverId and receiver $senderId is not found.")
        }
        conversation1.copy(lastMessage = text, updatedAt = Date.from(Instant.now())).let {
            repository.save(it)
        }
        conversation2.copy(lastMessage = text, updatedAt = Date.from(Instant.now())).let {
            repository.save(it)
        }
    }


    private fun createMessage(senderId: String, receiverId: String): String {
        val messageId = UUID.randomUUID().toString()
        val senderRecipient = Conversation().copy(
            chatId = messageId,
            sender = senderId,
            receiver = receiverId
        )
        val recipientSender = Conversation().copy(
            chatId = messageId,
            sender = receiverId,
            receiver = senderId
        )
        repository.save(senderRecipient)
        repository.save(recipientSender)
        return messageId
    }

    private fun getProfessor(username: String): Professor = professorRepository.findByUsername(username).orElseThrow {
        ProfessorNotFoundException("Professor with username $username is not found.")
    }

    private fun getStudent(username: String): Student = studentRepository.findByUsername(username).orElseThrow {
        StudentNotFoundException("Student with username $username is not found.")
    }
}
