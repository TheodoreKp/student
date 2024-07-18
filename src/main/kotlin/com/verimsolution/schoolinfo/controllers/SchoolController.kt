package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.SchoolRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.SchoolService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "schools", produces = [MediaType.APPLICATION_JSON_VALUE])
class SchoolController(
    private val service: SchoolService
) {

    @GetMapping("")
    fun index(): ResponseEntity<HttpResponse> = successResponse(
        "Schools", OK, service.getSchools()
    )

    @PostMapping("", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun store(
        @ModelAttribute @Valid schoolRequest: SchoolRequest, result: BindingResult
    ): ResponseEntity<HttpResponse> = if (result.hasErrors()) {
        validationErrorResponse("Validation errors", result.fieldErrors)
    } else successResponse("School is created successfully", OK, service.saveSchool(schoolRequest))


    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "School details", OK, service.showSchool(id)
    )

    @PostMapping("/{id}", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun update(
        @PathVariable id: String, @ModelAttribute @Valid schoolRequest: SchoolRequest, result: BindingResult
    ): ResponseEntity<HttpResponse> = if (result.hasErrors()) {
        validationErrorResponse("Validation errors", result.fieldErrors)
    } else successResponse("School is updated successfully", OK, service.updateSchool(id, schoolRequest))

}
