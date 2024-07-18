package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ConversationService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(STUDENT_API_BASE_URL + "conversations", produces = [APPLICATION_JSON_VALUE])
class StudentConversationController(
    private val service: ConversationService
) {

    @GetMapping
    fun index(): ResponseEntity<HttpResponse> = successResponse(
        "List conversations", HttpStatus.OK, service.listStudentConversation()
    )

    @GetMapping("contacts")
    fun contact(): ResponseEntity<HttpResponse> = successResponse(
        "List conversations contact", HttpStatus.OK, service.listStudentConversationContacts()
    )
}