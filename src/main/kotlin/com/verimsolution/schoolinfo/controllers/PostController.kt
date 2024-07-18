package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.PostRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.PostService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "posts", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostController(
    val service: PostService
) {

    @GetMapping("school/{schoolId}")
    fun index(@PathVariable schoolId: String): ResponseEntity<HttpResponse> = successResponse(
        "Posts by SCHOOL", OK, service.getPosts(schoolId)
    )

    @PostMapping("school/{schoolId}", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun store(@PathVariable schoolId: String, @ModelAttribute @Valid request: PostRequest): ResponseEntity<HttpResponse> = successResponse(
        "Post is created successfully by SCHOOL", CREATED, service.storePost(schoolId, request)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Post details", OK, service.showPost(id)
    )

    @PutMapping("{id}", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun update(@ModelAttribute @Valid request: PostRequest, @PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Post is updated successfully by SCHOOL", OK, service.updatePost(id, request)
    )
}
