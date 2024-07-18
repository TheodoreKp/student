package com.verimsolution.schoolinfo.controllers.students

import com.verimsolution.schoolinfo.requests.ForumResponseRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ResponseService
import com.verimsolution.schoolinfo.utils.STUDENT_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(STUDENT_API_BASE_URL + "responses", produces = [APPLICATION_JSON_VALUE])
class StudentResponseController(
    private val service: ResponseService
) {

    @GetMapping("forum/{forumId}")
    fun list(@PathVariable forumId: String): ResponseEntity<HttpResponse> = successResponse(
        "Forum with id $forumId responses", OK, service.listResponseByList(forumId)
    )

    @PostMapping("forum/{forumId}", consumes = [APPLICATION_JSON_VALUE])
    fun store(
        @PathVariable forumId: String, @RequestBody @Valid request: ForumResponseRequest
    ): ResponseEntity<HttpResponse> = successResponse(
        "Response is created successfully", CREATED, service.saveStudentResponse(forumId, request)
    )

    @PutMapping("{id}/valid")
    fun valid(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Response is validated successfully", OK, service.validResponse(id)
    )

    @DeleteMapping("{id}")
    fun remove(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Response is removed successfully", OK, service.removeResponse(id)
    )
}
