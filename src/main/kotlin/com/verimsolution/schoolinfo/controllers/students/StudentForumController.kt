package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.requests.ForumRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ForumService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(STUDENT_API_BASE_URL + "forums", produces = [APPLICATION_JSON_VALUE])
class StudentForumController(
    private val service: ForumService
) {

    @GetMapping("me")
    fun index(): ResponseEntity<HttpResponse> = successResponse(
        "List forum", OK, service.listStudentForum(SecurityContextHolder.getContext().authentication)
    )

    @GetMapping("classrooms")
    fun list(): ResponseEntity<HttpResponse> = successResponse(
        "List classroom", OK, service.listStudentClassroomForum(SecurityContextHolder.getContext().authentication)
    )

    @PostMapping("", consumes = [APPLICATION_JSON_VALUE])
    fun store(@RequestBody @Valid request: ForumRequest): ResponseEntity<HttpResponse> = successResponse(
        "Forum is created successfully", CREATED, service.saveStudentForum(request)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Forum details", OK, service.showForum(id)
    )

    @PutMapping("{id}", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun update(@PathVariable id: String, @ModelAttribute @Valid request: ForumRequest): ResponseEntity<HttpResponse> =
        successResponse(
            "Forum is updated successfully", OK, service.updateForum(id, request)
        )

    @DeleteMapping("{id}")
    fun destroy(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Forum is deleted successfully", OK, service.destroyForum(id)
    )
}
