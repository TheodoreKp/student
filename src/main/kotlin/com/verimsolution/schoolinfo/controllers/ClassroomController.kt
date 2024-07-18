package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.ClassroomRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ClassroomService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import com.verimsolution.schoolinfo.utils.validationErrorResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "classrooms", produces = [APPLICATION_JSON_VALUE])
class ClassroomController(
    private val service: ClassroomService
) {

    @GetMapping("school/{schoolId}")
    fun index(@PathVariable schoolId: String): ResponseEntity<HttpResponse> = successResponse(
        "Schools", OK, service.getClassroomsBySchool(schoolId)
    )

    @PostMapping("school/{schoolId}", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun store(
        @ModelAttribute @Valid request: ClassroomRequest,
        result: BindingResult, @PathVariable schoolId: String
    ): ResponseEntity<HttpResponse> = if (result.hasErrors()) {
        validationErrorResponse("Validation errors", result.fieldErrors)
    } else
        successResponse(
            "Classroom is created successfully", CREATED, service.saveClassroom(schoolId, request)
        )


    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Classroom details", OK, service.showClassroom(id)
    )

    @PostMapping("/{id}", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun update(
        @PathVariable id: String, @ModelAttribute @Valid request: ClassroomRequest, result: BindingResult
    ): ResponseEntity<HttpResponse> = if (result.hasErrors()) {
        validationErrorResponse("Validation errors", result.fieldErrors)
    } else successResponse(
        "Classroom is updated successfully", OK, service.updateClassroom(id, request)
    )

}
