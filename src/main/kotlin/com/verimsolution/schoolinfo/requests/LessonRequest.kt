package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable
import lombok.Data
import org.springframework.web.multipart.MultipartFile

data class LessonRequest(
    @field:NotNull @field:NotBlank
    val name: String? = null,
    @field:NotNull @field:NotBlank
    val description: String? = null,
    @field:NotNull @field:NotBlank
    val classroomId: String? = null,
    @field:NotNull @field:NotBlank
    val subject: String? = null,
    @field:NotNull
    val cover: MultipartFile? = null
)
