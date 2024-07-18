package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.CommunicateRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.CommunicateService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "communicates", produces = [MediaType.APPLICATION_JSON_VALUE])
class CommunicateController(
    private val service: CommunicateService
) {

    @GetMapping("school/{schoolId}")
    fun index(@PathVariable schoolId: String): ResponseEntity<HttpResponse> = successResponse(
        "Communicates", OK, service.getCommunicates(schoolId)
    )

    @GetMapping("school/{schoolId}/classroom/{classroomId}")
    fun index(@PathVariable schoolId: String, @PathVariable classroomId: String?): ResponseEntity<HttpResponse> =
        successResponse(
            "Communicates", OK, service.getCommunicates(schoolId, classroomId)
        )

    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    fun store(@RequestBody @Valid request: CommunicateRequest): ResponseEntity<HttpResponse> = successResponse(
        "Communicate is created successfully", CREATED, service.saveCommunicate(request)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Communicate details", OK, service.showCommunicate(id)
    )

    @PutMapping("{id}", produces = [APPLICATION_JSON_VALUE])
    fun update(
        @PathVariable id: String, @RequestBody @Valid request: CommunicateRequest
    ): ResponseEntity<HttpResponse> = successResponse(
        "Communicate is updated successfully", OK, service.updateCommunicate(id, request)
    )
}
