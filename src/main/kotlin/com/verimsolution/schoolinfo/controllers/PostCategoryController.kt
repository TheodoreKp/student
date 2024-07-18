package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.requests.PostCategoryRequest
import com.verimsolution.schoolinfo.responses.HttpResponse
import com.verimsolution.schoolinfo.services.PostCategoryService
import com.verimsolution.schoolinfo.utils.SCHOOL_API_BASE_URL
import com.verimsolution.schoolinfo.utils.successResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SCHOOL_API_BASE_URL + "post/categories", produces = [MediaType.APPLICATION_JSON_VALUE])
class PostCategoryController(
    private val service: PostCategoryService
) {

    @GetMapping("school/{schoolId}")
    fun index(@PathVariable schoolId: String): ResponseEntity<HttpResponse> = successResponse(
        "Post Categories", OK, service.getCategories(schoolId)
    )

    @PostMapping("school/{schoolId}", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun store(
        @ModelAttribute @Valid request: PostCategoryRequest, @PathVariable schoolId: String
    ): ResponseEntity<HttpResponse> = successResponse(
        "Post Category is created successfully", CREATED, service.saveCategory(schoolId, request)
    )

    @GetMapping("{id}")
    fun show(@PathVariable id: String): ResponseEntity<HttpResponse> = successResponse(
        "Post Category details", OK, service.showCategory(id)
    )

    @PostMapping("{id}", consumes = [APPLICATION_FORM_URLENCODED_VALUE])
    fun update(
        @ModelAttribute @Valid request: PostCategoryRequest, @PathVariable id: String
    ): ResponseEntity<HttpResponse> = successResponse(
        "Post Category is updated successfully", OK, service.updateCategory(id, request)
    )
}
