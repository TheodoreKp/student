package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.MessageService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(PROFESSOR_API_BASE_URL + "messages", produces = [APPLICATION_JSON_VALUE])
class ProfessorMessageController(
    private val service: MessageService
) {


    @GetMapping("{senderId}/{receiverId}")
    fun index(@PathVariable senderId: String, @PathVariable receiverId: String): ResponseEntity<HttpResponse> =
        successResponse(
            "List conversations", HttpStatus.OK, service.list(senderId, receiverId)
        )
}