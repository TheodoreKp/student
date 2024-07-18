package com.verimsolution.schoolinfo.controllers.professors

import com.verimsolution.schoolinfo.requests.ForumResponseRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.ResponseService
import com.verimsolution.schoolinfo.utils.PROFESSOR_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PROFESSOR_API_BASE_URL + "responses", produces = [MediaType.APPLICATION_JSON_VALUE])
class ProfessorResponseController(
    private val service: ResponseService
) {

    @PostMapping("forum/{forumId}", consumes = [APPLICATION_JSON_VALUE])
    fun store(
        @PathVariable forumId: String, @RequestBody @Valid request: ForumResponseRequest, result: BindingResult
    ): ResponseEntity<HttpResponse> = successResponse(
        "Response is created successfully", CREATED, service.saveProfessorResponse(forumId, request)
    )

    @PutMapping("{id}/valid")
    fun valid(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Response is validated successfully", HttpStatus.OK, service.validResponse(id)
    )

    @DeleteMapping("{id}")
    fun remove(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Response is removed successfully", HttpStatus.OK, service.removeResponse(id)
    )
}
