package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.ProfessorClassroomsRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ProfessorService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "professors", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProfessorController(
    private val service: ProfessorService
) {

    @GetMapping
    fun index(): ResponseEntity<HttpResponse> = successResponse(
        "list of professors", OK, service.listAllProfessor()
    )

    @PutMapping("{id}/locked")
    fun locked(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor is locked successfully", OK, service.lockedProfessor(id)
    )

    @PutMapping("{id}/unlocked")
    fun unlocked(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor is unlocked successfully", OK, service.unlockedProfessor(id)
    )

    @PutMapping("{id}/enabled")
    fun enabled(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor is enabled successfully", OK, service.enabledProfessor(id)
    )

    @PutMapping("{id}/disabled")
    fun disabled(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor is disabled successfully", OK, service.disabledProfessor(id)
    )

    @PutMapping("{id}/classrooms", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun classroom(
        @RequestBody @Valid request: ProfessorClassroomsRequest, @PathVariable id: String
    ): ResponseEntity<HttpResponse> = successResponse(
        "Classrooms is affected professor successfully", OK, service.addProfessorClassrooms(id, request)
    )
}
