package com.verimsolution.schoolinfo.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PostCategoryRequest(
    @field:NotNull @field:NotBlank
    val name: String? = null
)
