package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.StudentRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.StudentService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "students", produces = [APPLICATION_JSON_VALUE])
class StudentController(private val service: StudentService) {

    @GetMapping("school/{schoolId}")
    fun index(@PathVariable schoolId: String): ResponseEntity<HttpResponse> = successResponse(
        "Students", OK, service.getStudents(schoolId)
    )

    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    fun store(@RequestBody @Valid request: StudentRequest): ResponseEntity<HttpResponse> = successResponse(
        "Student is created successfully", CREATED, service.saveStudent(request)
    )


    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Student details", OK, service.showStudent(id)
    )


    @PutMapping("{id}/enabled")
    fun enabled(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Student with id $id is enabled successfully", HttpStatus.OK, service.enabledStudent(id)
    )

    @PutMapping("{id}/disabled")
    fun disabled(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Student with id $id is disabled successfully", HttpStatus.OK, service.disabledStudent(id)
    )

    @PutMapping("{id}/locked")
    fun locked(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor with id $id is locked successfully", HttpStatus.OK, service.lockedStudent(id)
    )

    @PutMapping("{id}/unlocked")
    fun unlocked(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Professor with id $id is unlocked successfully", HttpStatus.OK, service.unlockedStudent(id)
    )
}
