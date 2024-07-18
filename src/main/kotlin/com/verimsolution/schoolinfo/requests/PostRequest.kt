package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class PostRequest(
    @field:NotNull @field:NotBlank
    val title: String?,
    @field:NotNull @field:NotBlank
    val introduction: String?,
    @field:NotNull @field:NotBlank
    val content: String?,
    @field:NotNull
    val image: MultipartFile?,
    @field:NotNull @field:NotBlank
    val categoryId: String?
)
