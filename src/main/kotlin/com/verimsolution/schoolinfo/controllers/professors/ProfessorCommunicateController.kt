package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.requests.CommunicateRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.CommunicateService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PROFESSOR_API_BASE_URL + "communicates", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProfessorCommunicateController(
    private val service: CommunicateService
) {

    @GetMapping
    fun index(): ResponseEntity<HttpResponse> = successResponse(
        "Communicates", OK, service.professorCommunicates(SecurityContextHolder.getContext().authentication)
    )

    @GetMapping("admin")
    fun list(): ResponseEntity<HttpResponse> = successResponse(
        "Communicates", OK, service.adminCommunicates(SecurityContextHolder.getContext().authentication)
    )


    @PostMapping("", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun store(@RequestBody @Valid request: CommunicateRequest): ResponseEntity<HttpResponse> = successResponse(
        "Communicate is created successfully", CREATED, service.saveProfessorCommunicate(request)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Communicate details", OK, service.showCommunicate( id)
    )
}
