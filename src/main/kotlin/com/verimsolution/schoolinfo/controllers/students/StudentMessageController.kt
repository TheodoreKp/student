package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.MessageService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(STUDENT_API_BASE_URL + "messages", produces = [MediaType.APPLICATION_JSON_VALUE])
class StudentMessageController(
    private val service: MessageService
) {

    @GetMapping("{senderId}/{receiverId}")
    fun index(@PathVariable senderId: String, @PathVariable receiverId: String): ResponseEntity<HttpResponse> =
        successResponse(
            "List conversations", OK, service.list(senderId, receiverId)
        )
}