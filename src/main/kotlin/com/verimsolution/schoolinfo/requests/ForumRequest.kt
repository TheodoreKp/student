package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.Data
import org.bson.types.ObjectId

@Data
data class ForumRequest (
    @field:NotNull @field:NotBlank
    val title: String? = null,
    @field:NotNull @field:NotBlank
    val description: String? = null,
    @field:NotNull @field:NotBlank
    val classroomId: String? = null
)
